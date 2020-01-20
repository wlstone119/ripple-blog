package com.ripple.blog.domain.service;

import java.util.List;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.domain.model.ConsoleData;
import com.ripple.blog.infrastructure.common.model.AuthContext;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;
import com.ripple.blog.infrastructure.dao.query.PostQuery;

public interface PostService {

	PostEntity createPost(AuthContext context, PostEntity entity);

	PageResult<PostEntity> find(PostQuery iquery, Page page);

	List<PostEntity> findHotPostBeforeTen();

	PostEntity findById(String id);

	PostEntity findByShamId(String ename, String shamId);

	long count();

	List<PostEntity> findRecentlyPublishedBeforeTen();

	PageResult<PostEntity> findPostByTagId(String id, Page page);

	PostEntity incPros(String id);

	long getWebSiteCount();

	ConsoleData getConsoleData();

	PostEntity enable(String id, boolean enable, AuthContext context);

	PostEntity delete(String id, AuthContext context);

	List<String> removeTopic(PostEntity entity, String id, String topicNames);

	List<String> removeTopic(List<String> removeTopicId);

	PostEntity incPros(String ename, String shamId);

	PostEntity enable(String ename, String shamId, boolean enable, AuthContext context);

	PostEntity findByShamIdInternal(String ename, String shamId, boolean includeTopicAncCount);
}
