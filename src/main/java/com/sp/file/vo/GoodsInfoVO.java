package com.sp.file.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsInfoVO {
	private int giNum;
	private String giName;
	private String giDesc;
	private List<GoodsFileInfoVO> goodsFiles;

}
