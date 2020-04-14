package com.ithe.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ithe.Utils.PageUtils;
import com.ithe.entity.Goods;
import com.ithe.service.AdminService;
import com.ithe.service.GoodsService;
import com.ithe.service.ImgsService;
import com.ithe.service.UserService;

@RestController
public class GoodsController {
	
	@Autowired
	public GoodsService goodsService;
	
	@Autowired
	public ImgsService imgsService;
	
	@Autowired
	public AdminService adminService;
	
	@Autowired
	public UserService userService;
	
	//分页查询
	@GetMapping("/showGoods/{pageNum}/{pageSize}")
	public Page<Goods> findByGoods(@PathVariable(value = "pageNum")  int pageNum,
			@PathVariable(value = "pageSize") int pageSize,Model model) {
		Page<Goods> findByPage = goodsService.findByPage(pageNum,pageSize); 
		List<Goods> list = findByPage.getContent();

		return findByPage;
	}
	
	//	首页
	@GetMapping("/index")
	public Page showGoods(Model model) {
		
		int page = 0; //page:当前页的索引。注意索引都是从0开始的。
		int size = 12;// size:每页显示3条数据
		
		Page<Goods> findByPage = goodsService.findByPage(page,size); 
		List<Goods> list =findByPage.getContent();
		
		return findByPage;
	}
	
	//模糊查询
	@GetMapping("/findGoodsLike/{goodsName}")
	public Page<Goods> findByLikeName(@PathVariable("goodsName")String name,Model model) {
		
		List<Goods> list = goodsService.findGoodsByLikeName(name);
		
		Pageable pageable = PageRequest.of(0,list.size());
		Page<Goods> pageList = PageUtils.createPageFromList(list, pageable);
		
		return pageList;
	}

	//修改时使用
	@RequestMapping("/goodsDetails/{id}")
	public Goods goodsDetails(@RequestParam("goodsId") Integer id ,Model model,HttpServletRequest req) {
		Goods goodsDetails = goodsService.findByid(id);
		System.err.println(goodsDetails);
		model.addAttribute("goodsDetails",goodsDetails);
		return goodsDetails;
		
	}



}
