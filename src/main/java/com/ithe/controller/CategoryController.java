package com.ithe.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.expression.Arrays;

import com.ithe.Utils.PageUtils;
import com.ithe.entity.Category;
import com.ithe.entity.Goods;
import com.ithe.service.CategoryService;

@RestController
public class CategoryController {
	
	
	@Autowired
	public CategoryService categoryService;
	
	//分类查询
	@GetMapping("/showCategory/{id}")
	public Page findCategoryById(@PathVariable int id) {
		System.out.println("+++++++++++++++++++"+id);
		Category cate = categoryService.findById(id);
		
		List<Goods> list = new ArrayList<Goods>(cate.getGoodsSet()); 
		Pageable pageable = PageRequest.of(0,list.size());
		
		Page<Goods> pageList = PageUtils.createPageFromList(list, pageable);
		
		return pageList;
	}

}
