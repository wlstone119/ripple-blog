package com.ijson.blog.service.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.ijson.blog.dao.*;
import com.ijson.blog.dao.entity.*;
import com.ijson.blog.dao.query.PostQuery;
import com.ijson.blog.exception.BlogBusinessExceptionCode;
import com.ijson.blog.exception.BlogNotFoundException;
import com.ijson.blog.manager.ViewSyncManager;
import com.ijson.blog.model.AuthContext;
import com.ijson.blog.model.Constant;
import com.ijson.blog.service.PostService;
import com.ijson.blog.service.model.ConsoleData;
import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * desc:
 * version: 6.7
 * Created by cuiyongxu on 2019/7/21 2:28 PM
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private CountDao countDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ViewSyncManager viewSyncManager;

    @Override
    public PostEntity createPost(AuthContext context, PostEntity entity) {
        return postDao.createOrUpdate(entity);
    }

    @Override
    public PageResult<PostEntity> find(PostQuery iquery, Page page) {
        PageResult<PostEntity> postEntityPageResult = postDao.find(iquery, page);
        Set<String> ids = postEntityPageResult.getDataList().stream().map(PostEntity::getId).collect(Collectors.toSet());
        Map<String, Long> countByIds = countDao.findCountByIds(ids);

        Map<String, Long> replyByIds = replyDao.findCountByIds(ids);

        List<PostEntity> dataList = postEntityPageResult.getDataList();

        Set<String> userIds = dataList.stream().map(PostEntity::getUserId).collect(Collectors.toSet());

        Map<String, String> userIdOrCname = userDao.batchCnameByIds(userIds);

        List<PostEntity> lastEntity = dataList.stream()
                .peek(key -> {
                    Long views = countByIds.get(key.getId());
                    if (Objects.isNull(views)) {
                        views = 0L;
                    }
                    key.setViews(views);

                    Long reply = replyByIds.get(key.getId());
                    if (Objects.isNull(reply)) {
                        reply = 0L;
                    }
                    String cname = userIdOrCname.get(key.getUserId());
                    key.setCname(Strings.isNullOrEmpty(cname)?Constant.UnknownUser:cname);
                    key.setReply(reply);
                }).collect(Collectors.toList());
        postEntityPageResult.setDataList(lastEntity);
        return postEntityPageResult;
    }

    @Cacheable(value = "hotPosts")
    @Override
    public List<PostEntity> findHotPostBeforeTen() {

        List<CountEntity> hotCount = countDao.findHot();
        List<String> hotIds = hotCount.stream().map(CountEntity::getId).collect(Collectors.toList());
        List<PostEntity> postEntities = postDao.findHotPostByIds(hotIds);
        if (CollectionUtils.isEmpty(postEntities)) {
            return Lists.newArrayList();
        }

        Map<String, Long> collect = hotCount.stream().collect(Collectors.toMap(CountEntity::getId, CountEntity::getViews));
        return postEntities.stream().map(post -> {
            post.setViews(collect.get(post.getId()));
            return post;
        }).collect(Collectors.toList());
    }

    @Override
    public PostEntity findById(String id) {
        PostEntity entity = postDao.find(id);
        if (Objects.isNull(entity)) {
            throw new BlogNotFoundException(BlogBusinessExceptionCode.BLOG_NOT_FOUND);
        }
        CountEntity countById = countDao.findCountById(id);
        if (Objects.nonNull(countById)) {
            entity.setViews(countById.getViews());
        }

        List<ReplyEntity> replyEntitys = replyDao.findCountById(id);
        if (CollectionUtils.isNotEmpty(replyEntitys)) {
            entity.setReply(replyEntitys.size());
        } else {
            entity.setReply(0);
        }

        List<TopicEntity> topicEntitys = topicDao.finds(entity.getTopicId());
        if (CollectionUtils.isNotEmpty(topicEntitys)) {
            entity.setTopicName(topicEntitys);
        }

        return entity;
    }

    @Override
    public PostEntity findByShamId(String ename, String shamId) {

        PostEntity entity = postDao.findByShamId(ename, shamId);
        if (Objects.isNull(entity)) {
            throw new BlogNotFoundException(BlogBusinessExceptionCode.BLOG_NOT_FOUND);
        }
        CountEntity countById = countDao.findCountById(entity.getId());
        if (Objects.nonNull(countById)) {
            entity.setViews(countById.getViews());
        }

        List<ReplyEntity> replyEntitys = replyDao.findCountById(entity.getId());
        if (CollectionUtils.isNotEmpty(replyEntitys)) {
            entity.setReply(replyEntitys.size());
        } else {
            entity.setReply(0);
        }

        List<TopicEntity> topicEntitys = topicDao.finds(entity.getTopicId());
        if (CollectionUtils.isNotEmpty(topicEntitys)) {
            entity.setTopicName(topicEntitys);
        }

        UserEntity userEntity = userDao.findById(entity.getUserId());
        if (Objects.nonNull(userEntity)) {
            entity.setCname(userEntity.getCname());
        }
        viewSyncManager.syncViewBlog(entity);

        return entity;
    }



    @Cacheable(value = "postCount")
    @Override
    public long count() {
        return postDao.count();
    }

    @Override
    public List<PostEntity> findRecentlyPublishedBeforeTen() {
        return postDao.getRecentlyPublished();
    }

    @Override
    public PageResult<PostEntity> findPostByTagId(String id, Page page) {

        return postDao.findPostByTagId(id, page);
    }

    @Override
    public PostEntity incPros(String id) {
        return postDao.incPros(id);
    }

    @Override
    public long getWebSiteCount() {
        CountEntity countById = countDao.findCountById(Constant.WEB_SITE_COUNT);
        return countById.getViews();
    }

    @Cacheable(value = "consoleData")
    @Override
    public ConsoleData getConsoleData() {
        Long publishTotal = postDao.findPublishTotal();
        Long readTotal = getWebSiteCount();
        Long commentTotal = 0L;
        Long todayPublishTotal = postDao.findTodayPublishTotal();

        return ConsoleData.create(publishTotal, readTotal, commentTotal, todayPublishTotal);
    }

    @Override
    public PostEntity enable(String id, boolean enable, AuthContext context) {
        return postDao.enable(id, enable, context.getId());
    }

    @Override
    public PostEntity delete(String id, AuthContext context) {
        //return postDao.delete(id, context.getId());
        postDao.delete(id);
        return null;
    }

    @Override
    public List<String> removeTopic(PostEntity postEntity, String id, String topicNames) {
        //库中该文章对应的topic
        List<TopicEntity> oldTopics = postEntity.getTopicName();
        //用户更新后的topic
        List<String> newTopics = Splitter.on(",").splitToList(topicNames);
        List<String> removeTopicId = Lists.newArrayList();
        for (TopicEntity entity : oldTopics) {
            //如果老的topic不再用户更新后的topic中
            if (!newTopics.contains(entity.getTopicName())) {
                removeTopicId.add(entity.getId());
            }
        }
        if (CollectionUtils.isEmpty(removeTopicId)) {
            return Lists.newArrayList();
        }

        topicDao.batchIncTopicCount(removeTopicId);
        return removeTopicId;
    }


    @Override
    public List<String> removeTopic(List<String> removeTopicId) {
        topicDao.batchIncTopicCount(removeTopicId);
        return removeTopicId;
    }

    @Override
    public PostEntity incPros(String ename, String shamId) {
        return postDao.incPros(ename, shamId);
    }

    @Override
    public PostEntity enable(String ename, String shamId, boolean enable, AuthContext context) {
        return postDao.enable(ename, shamId, enable, context.getId());
    }

    @Override
    public PostEntity findByShamIdInternal(String ename, String shamId,boolean includeTopicAncCount) {
        PostEntity entity = postDao.findByShamIdInternal(ename, shamId);

        if(includeTopicAncCount){
            CountEntity countById = countDao.findCountById(entity.getId());
            if (Objects.nonNull(countById)) {
                entity.setViews(countById.getViews());
            }

            List<ReplyEntity> replyEntitys = replyDao.findCountById(entity.getId());
            if (CollectionUtils.isNotEmpty(replyEntitys)) {
                entity.setReply(replyEntitys.size());
            } else {
                entity.setReply(0);
            }

            List<TopicEntity> topicEntitys = topicDao.finds(entity.getTopicId());
            if (CollectionUtils.isNotEmpty(topicEntitys)) {
                entity.setTopicName(topicEntitys);
            }

            UserEntity userEntity = userDao.findById(entity.getUserId());
            if (Objects.nonNull(userEntity)) {
                entity.setCname(userEntity.getCname());
            }
        }

        return entity;
    }
}
