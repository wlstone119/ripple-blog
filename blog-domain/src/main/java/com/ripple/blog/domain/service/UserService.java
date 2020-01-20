package com.ripple.blog.domain.service;

import java.util.Map;
import java.util.Set;

import com.ripple.blog.infrastructure.common.model.AuthContext;
import com.ripple.blog.infrastructure.dao.entity.UserEntity;

public interface UserService {

	AuthContext login(String ename, String password);

	UserEntity reg(UserEntity entity);

	UserEntity findUserByEname(String ename, String email, String mobile);

	UserEntity findUserById(String userId);

	UserEntity edit(UserEntity entity);

	Map<String, String> findCnameByIds(Set<String> userIds);

}
