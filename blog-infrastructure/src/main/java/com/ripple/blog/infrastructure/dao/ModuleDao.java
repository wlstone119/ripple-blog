package com.ripple.blog.infrastructure.dao;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.infrastructure.dao.entity.ModuleEntity;
import com.ripple.blog.infrastructure.dao.query.ModuleQuery;

public interface ModuleDao {

	ModuleEntity createOrUpdate(ModuleEntity entity);

	ModuleEntity find(String id);

	ModuleEntity enable(String id, boolean enable, String userId);

	PageResult<ModuleEntity> find(ModuleQuery query, Page page);

	ModuleEntity delete(String id, String userId);

	void delete(String id);
}
