package com.ithe.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ithe.Utils.MD5Utils;
import com.ithe.Utils.PageUtils;
import com.ithe.entity.Admin;
import com.ithe.entity.Goods;
import com.ithe.entity.Imgs;
import com.ithe.entity.User;
import com.ithe.service.GoodsService;
import com.ithe.service.IMailService;
import com.ithe.service.ImgsService;
import com.ithe.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public GoodsService goodsService;
	
    @Autowired
    private IMailService mailService;
    
    @Autowired
	public ImgsService imgsService;
	
	//用户登录
	@PostMapping("userLogin")
	public String userLogin(User user,Model model,HttpServletRequest req) {
		//查数据库中的user
		User use = userService.findByName(user.getUserName());
		String passWord ="";
		if(user.getUserPassword()!=null) {
			passWord = MD5Utils.md5(user.getUserPassword());
		}
		
		if(passWord!=null&&passWord.equals(use.getUserPassword())) {
			//转发查询商品列表
			req.getSession().setAttribute("exitUser", use);
			return "success";
		}else {
			return "error";
		}
		
	}
	
	//用户退出
	@GetMapping("/userExit")
	public String exitUser(Model model,HttpServletRequest req) {
		req.getSession().removeAttribute("exitUser");
		return "exit";
	}
	
	//用户个人中心
	@GetMapping("/userInfo")
	public String userInfo(Model model,HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute("exitUser");
	    if(user==null||user.getUserName()==null||"".equals(user.getUserName())) {
		  return "redirect:/index"; 
		}
		User userInfo = userService.findByName(user.getUserName());
		req.getSession().setAttribute("userInfo",userInfo);
		
		List<Goods> list = goodsService.findGoodsByUserId(userInfo.getUserId());
        Pageable pageable = PageRequest.of(0,list.size());
		Page<Goods> pageList = PageUtils.createPageFromList(list, pageable);
		model.addAttribute("page", pageList); 
		return "Info/userInfo";
	}
	
	
	//用户注册
	@RequestMapping("/userRegist")
	public String userRegist(User user,Model model,HttpServletRequest req) {
		
		mailService.sendSimpleMail(user.getUserEmail(),"主题：欢迎来到人文科技学院","内容：邮件发送成功");
		
		String pwd = user.getUserPassword();
		user.setUserPassword(MD5Utils.md5(pwd));
		userService.save(user);
		
		req.getSession().setAttribute("exitUser", user);
		return "success";
	}
	
	
	
	//用户添加商品
	@PostMapping("/addGoods")
	public String addGoods(Goods goods, Model model,
			@RequestParam("uploadFile") MultipartFile file,HttpServletRequest req) throws IOException {
	    if (file.isEmpty()) {
	    	
	   	model.addAttribute("fileMsg","上传失败");
	        return "/Info/goods/goodsAdd";
	    }
	    
	    //得到文件名
	    String fileName = file.getOriginalFilename();
	    
	    File directory = new File("src/main/resources"); 
	    //取绝对路径
        String courseFile = directory.getCanonicalPath();
        
        //拼接图片路径
	    File dest = new File(courseFile +"/static/img/"+ fileName);
	    
        //保存图片
        Imgs img = new Imgs();
        img.setImgUrl(fileName);
        img.setGoods(goods);
        imgsService.save(img);
        
        //图片关联商品
        Set<Imgs> simg = new HashSet<Imgs>();
        goods.setImgs(simg);
        
        //商品关联用户
        if(req.getSession().getAttribute("exitUser")!=null) {
        	User user=userService.findByName(((User) req.getSession().getAttribute("exitUser")).getUserName());
        	goods.setUser_goods(user);
        }
        
        Date currentDate = new Date(System.currentTimeMillis());
        goods.setGoodsModifiedTime(currentDate);
        //文件保存成功再保存数据到数据库
        try {
			file.transferTo(dest);
			goodsService.add(goods);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "success";
	}

	
	//删除商品
	@DeleteMapping("/deleteGoods/{id}")
	public String deleteGoods(@PathVariable("id") Integer id) throws IOException {
		Goods goods = goodsService.findByid(id);
		
		Set<Imgs> setimg = goods.getImgs();
		Iterator<Imgs> iterator = setimg.iterator();
		
		if(iterator.hasNext()) {
			Imgs img = iterator.next();
			String filename = img.getImgUrl();
	        File directory = new File("src/main/resources"); 
			//取绝对路径
		    String courseFile = directory.getCanonicalPath();
		    //拼接图片路径
		   File dest = new File(courseFile +"/static/img/"+ filename);
		   if(dest.exists()) {
			   dest.delete();
		   }
		}
    	goodsService.delete(id);
    	
    	return "ok";
	}

}
