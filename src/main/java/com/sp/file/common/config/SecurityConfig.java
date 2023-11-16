package com.sp.file.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.sp.file.common.service.LoginInfoService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final LoginInfoService loginService;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> {
			web.ignoring().antMatchers("/js/**", "/css/**", "/imgs/**");

		};
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity hs) throws Exception{
		hs.authorizeHttpRequests(req->
			req.antMatchers("/login","/form/join","/api/join","/html/login","/html/join", "/html/login-fail")
			.permitAll()
			.antMatchers("/html/root/index").hasRole("ROOT")
			.anyRequest()
			.authenticated()
		)
		
		.formLogin(f1->
		f1.loginPage("/html/login") //어떤 페이지로 들어와도 로그인 페이지로 가라
		.usernameParameter("liId")
		.passwordParameter("liPwd")
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/")
		.failureUrl("/html/login-fail")
		
		)
		
		.logout(logout->
		logout.logoutUrl("/logout")
		.logoutSuccessUrl("/html/login")
		)
		
		.csrf(csrf->csrf.disable())
		.exceptionHandling(ex->ex.accessDeniedPage("/html/denied"))
		.userDetailsService(loginService);
		return hs.build();
		
	}

}

//filter vs antMatchers
