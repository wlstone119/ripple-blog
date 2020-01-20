package com.ripple.blog.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ripple.blog.domain.service.RoleService;
import com.ripple.blog.infrastructure.dao.RoleDao;
import com.ripple.blog.infrastructure.dao.entity.RoleEntity;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public RoleEntity create(RoleEntity roleEntity) {
		return roleDao.create(roleEntity);
	}

	@Override
	public RoleEntity findById(String roleId) {
		return roleDao.find(roleId);
	}
}
