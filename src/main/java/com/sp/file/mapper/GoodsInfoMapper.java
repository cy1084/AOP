package com.sp.file.mapper;

import java.util.List;

import com.sp.vo.GoodsInfoVO;

public interface GoodsInfoMapper {
	int insertGoodsInfo(GoodsInfoVO goods);
	List<GoodsInfoVO> selectGoodsInfos(GoodsInfoVO goods);
	GoodsInfoVO selectGoodsInfo(int giNum);
	

}
