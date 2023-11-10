package com.sp.file.service;

import java.util.List;

import com.sp.vo.GoodsInfoVO;

public interface GoodsInfoService {
	int insertGoodsInfo(GoodsInfoVO goods);
	int updateGoodsInfo(GoodsInfoVO goods);
	List<GoodsInfoVO> selectGoodsInfos(GoodsInfoVO goods);
	GoodsInfoVO selectGoodsInfo(int giNum);
	
}
