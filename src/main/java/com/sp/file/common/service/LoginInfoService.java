package com.sp.file.common.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sp.file.mapper.LoginInfoMapper;
import com.sp.file.mapper.RoleInfoMapper;
import com.sp.file.vo.LoginInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginInfoService implements UserDetailsService {
	
	private final LoginInfoMapper loginMapper;
	private final RoleInfoMapper roleMapper;
	private final PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		LoginInfoVO login=loginMapper.selectLoginInfoByLiId(username);
		if(login==null) {
			throw new UsernameNotFoundException("아이디나 비밀번호가 잘못되었습니다.");
		}
		login.setAuthorities(roleMapper.selectRoleInfoByLiNum(login.getLiNum()));
		return login;
	}

	public int join(LoginInfoVO login) {
		login.setLiPwd(encoder.encode(login.getLiPwd()));
		return loginMapper.insertLoginInfo(login);
	}
}
