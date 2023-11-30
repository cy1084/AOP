package com.sp.file.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.file.common.provider.JWTProvider;

public class JWTFilter extends OncePerRequestFilter{
	public final JWTProvider jwtProvider;
	
	public JWTFilter(JWTProvider jwtProvider) {
		this.jwtProvider=jwtProvider;
	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authJwt=request.getHeader("Authorization");
		//authJwt="Bearer 12321321211212312123";
		authJwt=authJwt.replace("Bearer ", "");
		if(!jwtProvider.validateJWT(authJwt)) {
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			Map<String,String> res=new HashMap<>();
			res.put("msg", "토큰이 잘못되었습니다.");
			ObjectMapper om = new ObjectMapper();
			response.getWriter().print(om.writeValueAsString(res));
			return;
		}
	}
	filterChain.doFilter(request, response);
}

}
