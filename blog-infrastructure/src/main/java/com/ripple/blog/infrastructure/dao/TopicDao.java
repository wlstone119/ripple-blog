package com.ripple.blog.infrastructure.dao;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.infrastructure.dao.entity.TopicEntity;
import com.ripple.blog.infrastructure.dao.query.TopicQuery;

import java.util.List;

public interface TopicDao {

	TopicEntity createOrUpdate(TopicEntity entity);

	TopicEntity find(String id);

	List<TopicEntity> finds(List<String> ids);

	TopicEntity enable(String id, boolean enable, String userId);

	PageResult<TopicEntity> find(TopicQuery query, Page page);

	TopicEntity delete(String id, String userId);

	void delete(String id);

	TopicEntity findByTopicName(String tipicName);

	TopicEntity inc(String id);

	List<TopicEntity> finHotTag();

	List<TopicEntity> findAll();

	void batchIncTopicCount(List<String> removeTopicId);

	void updateShamIdTest(TopicEntity entity);

	TopicEntity findByShamId(String ename, String shamId);
}
