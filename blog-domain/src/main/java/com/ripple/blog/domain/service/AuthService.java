package com.ripple.blog.domain.service;

import com.ripple.blog.domain.model.LoginUser;

public interface AuthService {

	LoginUser getAuthByUserId(String ename);
	
}
