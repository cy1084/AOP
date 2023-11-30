package com.sp.file.common.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper om;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		Map<String, String> res = new HashMap<>();
		res.put("msg", "권한 없음");
		response.getWriter().print(om.writeValueAsString(res));

	}

	public static void main(String[] args) {
		log.info("status code:{}", HttpStatus.FORBIDDEN);
	}

}













