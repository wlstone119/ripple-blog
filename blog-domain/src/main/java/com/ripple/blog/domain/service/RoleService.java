package com.ripple.blog.domain.service;

import com.ripple.blog.infrastructure.dao.entity.RoleEntity;

public interface RoleService {

    RoleEntity create(RoleEntity roleEntity);

    RoleEntity findById(String roleId);
}
