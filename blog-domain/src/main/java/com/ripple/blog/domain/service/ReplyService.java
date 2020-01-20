package com.ripple.blog.domain.service;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.infrastructure.dao.entity.ReplyEntity;
import com.ripple.blog.infrastructure.dao.query.ReplyQuery;

public interface ReplyService {

	PageResult<ReplyEntity> find(ReplyQuery replyQuery, Page page);

	ReplyEntity save(String content, String shamId, String ename, ReplyEntity entity);

}
