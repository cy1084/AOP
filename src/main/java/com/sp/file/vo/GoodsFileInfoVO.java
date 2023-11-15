package com.sp.file.vo;

import org.springframework.web.multipart.MultipartFile;

import com.sp.file.type.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsFileInfoVO {
	private int gfiNum;
	private String gfiPath;
	private String gfiOriginName;
	private int giNum;
	private MultipartFile file;
	private Status status;
}
