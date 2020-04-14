package com.ithe.demo;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ithe.dao.GoodsDao;
import com.ithe.entity.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootVueApplicationTests {
	
	@Autowired
	public GoodsDao goodsDao;

	@Test
	void contextLoads() {
		Goods goo = goodsDao.findById(47).get();
		System.out.println(goo.getGoodsName()+goo.getImgs());
		goodsDao.deleteById(47);
	}

}
