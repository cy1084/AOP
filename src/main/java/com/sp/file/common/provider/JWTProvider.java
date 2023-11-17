package com.sp.file.common.provider;

import java.security.Key;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sp.file.vo.LoginInfoVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTProvider {
	private final String jwtSecret;
	private final int jwtExpire;
	
	public JWTProvider(@Value("jwt.Secret") String jwtSecret, @Value("jwt.expire") int jwtExpire) {
		this.jwtExpire=jwtExpire;
		this.jwtSecret=jwtSecret;
		
	}

	public String generateToken(LoginInfoVO login) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, jwtExpire);

		Map<String, Object> claim = new HashMap<>();
		claim.put("liId", login.getLiId());
		claim.put("liName", login.getLiName());

		byte[] secrets = DatatypeConverter.parseBase64Binary(jwtSecret);
		Key key=new SecretKeySpec(secrets,"HmacSHA256");
		
		JwtBuilder jb = Jwts.builder()
				.setClaims(claim)
				.signWith(SignatureAlgorithm.HS256, key)
				.setExpiration(cal.getTime());

		return jb.compact();
	}
	
	public LoginInfoVO decodeToken(String jwt) {
		try{
			Claims claims=Jwts.parser()		
		.setSigningKey(jwtSecret)
		.parseClaimsJws(jwt).getBody();
		LoginInfoVO login=new LoginInfoVO();
		login.setLiId(claims.get("liId").toString());
		login.setLiName(claims.get("liName").toString());
		return login;
		}catch(ExpiredJwtException eje) {
			log.error(eje.getMessage());
		}catch(JwtException je) {
			log.error(je.getMessage());
		}
	
		return null;
	}

	public static void main(String[] args) {
		/*
		JWTProvider p = new JWTProvider();
		LoginInfoVO login=new LoginInfoVO();
		login.setLiId("test");
		login.setLiName("홍길동");
		String jwt=p.generateToken(login);
		p.decodeToken(jwt); 
		//(jwt) <-여기에, 실행 후 나온 jwt 대입해야 함
		
		log.info("login=>{}",login);
		*/
	}
}
