package com.ripple.blog.domain.service.impl;

import com.google.common.collect.Lists;
import com.ripple.blog.domain.service.TopicService;
import com.ripple.blog.infrastructure.common.model.AuthContext;
import com.ripple.blog.infrastructure.dao.TopicDao;
import com.ripple.blog.infrastructure.dao.entity.TopicEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TopicServicesImpl implements TopicService {

	@Autowired
	private TopicDao topicDao;

	@Override
	public List<TopicEntity> findTopicByTopicNameAndIncCount(String topicNames, AuthContext context) {
		String[] topics = topicNames.split(",");
		TopicEntity topicEntity;
		List<TopicEntity> topicEntities = Lists.newArrayList();
		for (String topic : topics) {
			topicEntity = topicDao.findByTopicName(topic);
			if (Objects.isNull(topicEntity)) {
				topicEntity = topicDao.createOrUpdate(TopicEntity.create(topic, context));
			} else {
				topicEntity = topicDao.inc(topicEntity.getId());
			}
			if (Objects.nonNull(topicEntity)) {
				topicEntities.add(topicEntity);
			}
		}
		return topicEntities;
	}

	@Cacheable(value = "hotTopic")
	@Override
	public List<TopicEntity> findHotTag() {
		return topicDao.finHotTag();
	}

	@Override
	public List<TopicEntity> findAll() {
		return topicDao.findAll();
	}

	@Override
	public TopicEntity find(String id) {
		return topicDao.find(id);
	}

	@Override
	public TopicEntity findTopicByShamIdAndEname(String ename, String shamId) {
		return topicDao.findByShamId(ename, shamId);
	}
}
