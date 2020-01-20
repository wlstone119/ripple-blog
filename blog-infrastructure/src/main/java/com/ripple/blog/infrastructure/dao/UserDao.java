package com.ripple.blog.infrastructure.dao;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.infrastructure.dao.entity.UserEntity;
import com.ripple.blog.infrastructure.dao.query.UserQuery;

import java.util.Map;
import java.util.Set;

public interface UserDao {

	UserEntity create(UserEntity entity);

	UserEntity update(UserEntity entity);

	UserEntity findById(String id);

	UserEntity findByEname(String ename);

	PageResult<UserEntity> find(UserQuery query, Page page);

	Map<String, String> batchCnameByIds(Set<String> userIds);

	UserEntity enable(String id, boolean enable, String userId);

	void delete(String id);

	UserEntity delete(String id, String userId);

}
