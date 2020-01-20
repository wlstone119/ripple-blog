package com.ripple.blog.infrastructure.dao.query;

import lombok.Data;

@Data
public class PostQuery {

	private String id;

	private String topicId;

	private String userId;

	private String content;

	private long pros;

	private long cons;

	private String title;

	private Integer pageSize;

	private Integer pageNumber;

	private Boolean enable;

	/**
	 * title like 的查询
	 */
	private volatile boolean likeTitle;

}
