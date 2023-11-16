package com.sp.file.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.file.service.GoodsFileInfoService;
import com.sp.file.service.GoodsInfoService;
import com.sp.file.vo.GoodsInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GoodsInfoController {

	private final GoodsInfoService goodsService;
	private final GoodsFileInfoService goodsFileService;

	@GetMapping("/goods-infos")
	public List<GoodsInfoVO> getGoodsInfos(GoodsInfoVO goods) {
		return goodsService.selectGoodsInfos(goods);
	}

	@GetMapping("/goods-infos/{giNum}")
	public GoodsInfoVO getGoodsInfo(@PathVariable int giNum) {
		return goodsService.selectGoodsInfo(giNum);
	}

	@PostMapping("/goods-infos")
	public int addGoodsInfos(GoodsInfoVO goods) {
		return goodsService.insertGoodsInfo(goods);
		// 왜 이렇게 짜야하는지 생각!! 매우 중요!!
	}
	
	@PatchMapping("/goods-infos")
	public int modifyGoodsInfos(GoodsInfoVO goods) {
		return goodsService.updateGoodsInfo(goods);
		
	}
}
