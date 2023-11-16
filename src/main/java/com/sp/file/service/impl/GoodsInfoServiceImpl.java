package com.sp.file.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sp.file.mapper.GoodsInfoMapper;
import com.sp.file.service.GoodsFileInfoService;
import com.sp.file.service.GoodsInfoService;
import com.sp.file.vo.GoodsFileInfoVO;
import com.sp.file.vo.GoodsInfoVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GoodsInfoServiceImpl implements GoodsInfoService {

	private final GoodsInfoMapper goodsMapper;
	private final GoodsFileInfoService goodsFileService;

	@Override
	@Transactional
	public int insertGoodsInfo(GoodsInfoVO goods) {
		int result = goodsMapper.insertGoodsInfo(goods); // 인서트 후 giNum 생성
		result += goodsFileService.insertGoodsFileInfos(goods.getGiNum(), goods.getGoodsFiles());
		return result;

	}

	@Override
	public List<GoodsInfoVO> selectGoodsInfos(GoodsInfoVO goods) {

		return goodsMapper.selectGoodsInfos(goods);
	}

	@Override
	public GoodsInfoVO selectGoodsInfo(int giNum) {
		GoodsInfoVO goodsInfo = goodsMapper.selectGoodsInfo(giNum);
		List<GoodsFileInfoVO> files = goodsFileService.selectGoodsFileInfos(giNum);
		goodsInfo.setGoodsFiles(files);
		return goodsInfo;
	}

	@Override
	public int updateGoodsInfo(GoodsInfoVO goods) {
		int result = goodsMapper.updateGoodsInfo(goods);
		result += goodsFileService.updateGoodsFileInfos(goods.getGiNum(), goods.getGoodsFiles());
		return result;
	}

}
