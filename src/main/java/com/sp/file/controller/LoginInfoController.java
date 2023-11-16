package com.sp.file.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.file.common.service.LoginInfoService;
import com.sp.file.vo.LoginInfoVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginInfoController {
	private final LoginInfoService loginService;

	// 화면을 이동해야 하기 때문에 RestController 안됨

	@PostMapping("/form/join")
	public String join(LoginInfoVO login) {
		return "/html/join";
	}

	@PostMapping("/api/join")
	@ResponseBody
	public Map<String, Integer> apiJoin(@RequestBody  LoginInfoVO login) {
		Map<String, Integer> res = new HashMap<>();
		res.put("result", loginService.join(login));
		return res;
	}

}
