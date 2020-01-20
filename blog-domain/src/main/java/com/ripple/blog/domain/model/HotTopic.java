package com.ripple.blog.domain.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import com.ripple.blog.infrastructure.dao.entity.TopicEntity;

@Data
public class HotTopic {
	private String id;
	private String topicName;
	private long count;
	private String ename;
	private String shamId;

	public static List<HotTopic> getHotTopic(List<TopicEntity> topicEntities) {

		return topicEntities.stream().map(key -> {

			HotTopic hotTopic = new HotTopic();
			hotTopic.setId(key.getId());
			hotTopic.setEname(key.getEname());
			hotTopic.setShamId(key.getShamId());
			hotTopic.setCount(key.getPostCount());
			hotTopic.setTopicName(key.getTopicName());

			return hotTopic;

		}).sorted((o1, o2) -> {

			return o1.getCount() > o2.getCount() ? -1 : 1;

		}).collect(Collectors.toList());

	}
}
