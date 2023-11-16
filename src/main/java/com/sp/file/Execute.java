package com.sp.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

interface AuthInter{
	String getAuth();
}
@Getter
@Setter
@ToString
class Auth implements AuthInter{@Override
	public String getAuth() {
		return null;
	}
	
}


@Getter
@Setter
@ToString
class UserInfoVO{
	public Collection<? extends AuthInter> getAuthInter(){
		List<Auth> list = new ArrayList<>();
		return list;
	}
}

public class Execute {

	public static void main(String[] args) {
		
	}
}