package com.ripple.blog.domain.service.impl;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.domain.service.ReplyService;
import com.ripple.blog.infrastructure.common.exception.BlogBusinessExceptionCode;
import com.ripple.blog.infrastructure.common.exception.ReplyCreateException;
import com.ripple.blog.infrastructure.common.util.sensitive.SensitiveFilter;
import com.ripple.blog.infrastructure.dao.ReplyDao;
import com.ripple.blog.infrastructure.dao.entity.ReplyEntity;
import com.ripple.blog.infrastructure.dao.query.ReplyQuery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;

	@Override
	public PageResult<ReplyEntity> find(ReplyQuery replyQuery, Page page) {
		return replyDao.find(replyQuery, page);
	}

	@Override
	public ReplyEntity save(String content, String shamId, String ename, ReplyEntity entity) {
		// 使用默认单例（加载默认词典）
		SensitiveFilter filter = SensitiveFilter.DEFAULT;
		// 待过滤的句子
		if (content.length() > 300) {
			throw new ReplyCreateException(BlogBusinessExceptionCode.COMMENTS_SHOULD_NOT_EXCEED_300_WORDS);
		}
		// 进行过滤
		boolean filted = filter.filter(content);
		if (filted) {
			log.warn("存在敏感字:", content);
			throw new ReplyCreateException(BlogBusinessExceptionCode.SENSITIVE_TEXT_EXISTS_PLEASE_CHECK_AND_RESUBMIT);
		}
		entity.setShamId(shamId);
		entity.setEname(ename);
		return replyDao.createOrUpdate(entity);
	}
}
