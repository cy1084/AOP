package com.sp.file.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTMLController {
	@GetMapping("/html/**")
		public void goPage() {
		
	}
	@GetMapping("/")
	public String home() {
		return "/html/index";
	}
	

}
