package com.sp.file.mapper;


import com.sp.file.vo.LoginInfoVO;

public interface LoginInfoMapper {
	LoginInfoVO selectLoginInfoByLiId(String liId);
	int insertLoginInfo(LoginInfoVO login);
}
