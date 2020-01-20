package com.ripple.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ripple.blog.domain.model.LoginUser;
import com.ripple.blog.domain.service.AuthService;
import com.ripple.blog.domain.service.UserService;
import com.ripple.blog.infrastructure.dao.RoleDao;
import com.ripple.blog.infrastructure.dao.entity.UserEntity;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserService userService;

	/**
	 * 非高频操作
	 *
	 * @return
	 */
	@Override
	public LoginUser getAuthByUserId(String ename) {

		UserEntity user = userService.findUserByEname(ename, null, null);

		LoginUser loginUser = new LoginUser();
		loginUser.setCname(user.getCname());
		loginUser.setEname(user.getEname());
		loginUser.setUserId(user.getId());

		return loginUser;
	}
}
