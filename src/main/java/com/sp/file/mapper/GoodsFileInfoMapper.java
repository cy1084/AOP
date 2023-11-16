package com.sp.file.mapper;

import java.util.List;

import com.sp.file.vo.GoodsFileInfoVO;

public interface GoodsFileInfoMapper {
	int insertGoodsFileInfo(GoodsFileInfoVO goodsFile);
	int updateGoodsFileInfo(GoodsFileInfoVO goodsFile);
	int deleteGoodsFileInfo(int gfiNum);
	List<GoodsFileInfoVO> selectGoodsFileInfos(int giNum);
	GoodsFileInfoVO selectGoodsFileInfo(int giNum);
}
