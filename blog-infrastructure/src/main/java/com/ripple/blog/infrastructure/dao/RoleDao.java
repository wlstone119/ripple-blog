package com.ripple.blog.infrastructure.dao;

import com.ripple.blog.infrastructure.dao.entity.RoleEntity;

public interface RoleDao {

	RoleEntity create(RoleEntity entity);

	RoleEntity update(RoleEntity entity);

	RoleEntity find(String roleId);

}
