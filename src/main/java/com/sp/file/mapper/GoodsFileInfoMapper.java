package com.sp.file.mapper;

import java.util.List;

import com.sp.file.vo.GoodsFileInfoVO;

public interface GoodsFileInfoMapper {
	int insertGoodsFileInfo(GoodsFileInfoVO goodsFile);
	List<GoodsFileInfoVO> selectGoodsFileInfos(int giNum);
	GoodsFileInfoVO selectGoodsFileInfo(int giNum);
}
