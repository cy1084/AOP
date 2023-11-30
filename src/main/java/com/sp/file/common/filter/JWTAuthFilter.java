package com.sp.file.common.filter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.file.common.provider.JWTProvider;
import com.sp.file.common.service.LoginInfoService;
import com.sp.file.vo.LoginInfoVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authManager;
	private final LoginInfoService loginService;
	private final JWTProvider jwtProvider;
	private final ObjectMapper om;

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginInfoVO login;
		try {
			ObjectMapper om = new ObjectMapper();
			login = om.readValue(request.getInputStream(), LoginInfoVO.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getLiId(), login.getLiPwd());
			return getAuthenticationManager().authenticate(authToken);
		} catch (Exception e) {
			log.info("login error=>{}", e);
		}
		return null;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		LoginInfoVO login = (LoginInfoVO)authResult.getPrincipal();
		String token = jwtProvider.generateToken(login);
		response.addHeader("Authorization", "Bearer " + token);
		getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
	}
}