package com.ripple.blog.domain.service;

import java.util.List;

import com.ripple.blog.infrastructure.common.model.AuthContext;
import com.ripple.blog.infrastructure.dao.entity.TopicEntity;

public interface TopicService {

	List<TopicEntity> findTopicByTopicNameAndIncCount(String topicName, AuthContext context);

	List<TopicEntity> findHotTag();

	List<TopicEntity> findAll();

	TopicEntity find(String id);

	TopicEntity findTopicByShamIdAndEname(String ename, String shamId);

}
