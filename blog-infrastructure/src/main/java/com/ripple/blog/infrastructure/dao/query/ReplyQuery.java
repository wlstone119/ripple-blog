package com.ripple.blog.infrastructure.dao.query;

import lombok.Data;

@Data
public class ReplyQuery {

	private String id;

	private String topicId;

	private String userId;

	private String postId;

	private String content;
	private String ename;
	private String shamId;

}
