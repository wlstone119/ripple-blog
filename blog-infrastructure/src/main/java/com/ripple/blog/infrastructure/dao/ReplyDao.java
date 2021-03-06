package com.ripple.blog.infrastructure.dao;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.infrastructure.dao.entity.ReplyEntity;
import com.ripple.blog.infrastructure.dao.query.ReplyQuery;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReplyDao {

	ReplyEntity createOrUpdate(ReplyEntity entity);

	ReplyEntity find(String id);

	ReplyEntity enable(String id, boolean enable, String userId);

	PageResult<ReplyEntity> find(ReplyQuery query, Page page);

	ReplyEntity delete(String id, String userId);

	void delete(String id);

	Map<String, Long> findCountByIds(Set<String> ids);

	List<ReplyEntity> findCountById(String id);

	List<ReplyEntity> findAllTest();

	void updateShamIdTest(ReplyEntity entity);
}
