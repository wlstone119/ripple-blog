package com.ripple.blog.infrastructure.dao.query;

import lombok.Data;

@Data
public class TopicQuery {

	private String id;

	private String userId;

	private String moduleId;

	private long postCount;

}
