package com.ripple.blog.infrastructure.dao;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;
import com.ripple.blog.infrastructure.dao.query.PostQuery;

import java.util.List;

public interface PostDao {

	PostEntity createOrUpdate(PostEntity entity);

	PostEntity updateShamIdTest(PostEntity entity);

	List<PostEntity> findAllTest();

	PostEntity find(String id);

	PostEntity findByShamId(String ename, String shamId);

	PostEntity enable(String id, boolean enable, String userId);

	PageResult<PostEntity> find(PostQuery query, Page page);

	PostEntity delete(String id, String userId);

	void delete(String id);

	List<PostEntity> findHotPostByIds(List<String> hotIds);

	long count();

	List<PostEntity> getRecentlyPublished();

	PageResult<PostEntity> findPostByTagId(String id, Page page);

	PostEntity incPros(String id);

	Long findPublishTotal();

	Long findTodayPublishTotal();

	PostEntity incPros(String ename, String shamId);

	PostEntity enable(String ename, String shamId, boolean enable, String userId);

	PostEntity findByShamIdInternal(String ename, String shamId);
}
