package com.ijson.blog.dao.impl;

import com.google.common.base.Strings;
import com.ijson.blog.dao.PostDao;
import com.ijson.blog.dao.entity.PostEntity;
import com.ijson.blog.dao.query.PostQuery;
import com.ijson.blog.util.DateUtils;
import com.ijson.mongo.generator.util.ObjectId;
import com.ijson.mongo.support.AbstractDao;
import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.mongodb.WriteConcern;
import org.apache.commons.collections.CollectionUtils;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
public class PostDaoImpl extends AbstractDao<PostEntity> implements PostDao {


    @Override
    public PostEntity createOrUpdate(PostEntity entity) {
        if (!Strings.isNullOrEmpty(entity.getId())) {
            entity = findAndModify(entity);
        } else {
            ObjectId id = new ObjectId();
            entity.setId(id.toHexString());
            entity.setShamId(id.getTimestamp() + "");
            datastore.save(entity);
            return entity;
        }
        return entity;
    }

    @Override
    public PostEntity updateShamIdTest(PostEntity entity) {
        Query<PostEntity> query = createQuery();
        query.field(PostEntity.Fields.id).equal(entity.getId());
        UpdateOperations operations = createUpdateOperations();
        ObjectId objectId = new ObjectId(entity.getId());
        operations.set(PostEntity.Fields.shamId, objectId.getTimestamp() + "");
        return datastore.findAndModify(query, operations);
    }

    @Override
    public List<PostEntity> findAllTest() {
        Query<PostEntity> query = createQuery();
        return query.asList();
    }

    private PostEntity findAndModify(PostEntity entity) {
        Query<PostEntity> query = createQuery();
        query.field(PostEntity.Fields.id).equal(entity.getId());
        UpdateOperations operations = createUpdateOperations();

        if (CollectionUtils.isNotEmpty(entity.getTopicId())) {
            operations.set(PostEntity.Fields.topicId, entity.getTopicId());
        }

        if (!Strings.isNullOrEmpty(entity.getContent())) {
            operations.set(PostEntity.Fields.content, entity.getContent());
        }

        if (!Strings.isNullOrEmpty(entity.getTitle())) {
            operations.set(PostEntity.Fields.title, entity.getTitle());
        }
        operations.set(PostEntity.Fields.lastModifiedTime, System.currentTimeMillis());
        return datastore.findAndModify(query, operations);
    }

    @Override
    public PostEntity find(String id) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.id).equal(id);
        return query.get();
    }

    @Override
    public PostEntity findByShamId(String ename, String shamId) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.shamId).equal(shamId);
        query.field(PostEntity.Fields.ename).equal(ename);
        query.field(PostEntity.Fields.enable).equal(Boolean.TRUE);
        query.field(PostEntity.Fields.deleted).equal(Boolean.FALSE);
        return query.get();
    }

    @Override
    public PostEntity enable(String id, boolean enable, String userId) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.id).equal(id);
        UpdateOperations<PostEntity> updateOperations = datastore.createUpdateOperations(PostEntity.class);
        updateOperations.set(PostEntity.Fields.enable, enable);
        updateOperations.set(PostEntity.Fields.lastModifiedBy, userId);
        updateOperations.set(PostEntity.Fields.lastModifiedTime, System.currentTimeMillis());
        return datastore.findAndModify(query, updateOperations);
    }

    @Override
    public PageResult<PostEntity> find(PostQuery iquery, Page page) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);

        if (!Strings.isNullOrEmpty(iquery.getId())) {
            query.field(PostEntity.Fields.id).equal(iquery.getId());
        }

        if (!Strings.isNullOrEmpty(iquery.getTitle())) {
            if (iquery.isLikeTitle()) {
                query.or(query.criteria(PostEntity.Fields.title).containsIgnoreCase(iquery.getTitle()),
                        query.criteria(PostEntity.Fields.content).containsIgnoreCase(iquery.getTitle()));
            } else {
                query.field(PostEntity.Fields.title).equal(iquery.getTitle());
            }
        }
        query.field(PostEntity.Fields.deleted).equal(false);
        if (Objects.nonNull(iquery.getEnable())) {
            query.field(PostEntity.Fields.enable).equal(iquery.getEnable());
        }

        if (page.getOrderBy() == null) {
            query.order("-" + PostEntity.Fields.lastModifiedTime);//添加排序
        }
        if (page.getPageNumber() > 0) {
            query.offset((page.getPageNumber() - 1) * page.getPageSize()).limit(page.getPageSize());
        }

        long totalNum = query.countAll();
        List<PostEntity> entities = query.asList();

        PageResult<PostEntity> ret = new PageResult<>();
        ret.setDataList(entities);
        ret.setTotal(totalNum);
        return ret;
    }

    @Override
    public PostEntity delete(String id, String userId) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.createdBy).equal(userId);
        query.field(PostEntity.Fields.id).equal(id);
        UpdateOperations<PostEntity> updateOperations = datastore.createUpdateOperations(PostEntity.class);
        updateOperations.set(PostEntity.Fields.lastModifiedBy, userId);
        updateOperations.set(PostEntity.Fields.deleted, true);
        updateOperations.set(PostEntity.Fields.lastModifiedTime, System.currentTimeMillis());
        return datastore.findAndModify(query, updateOperations);
    }

    @Override
    public void delete(String id) {
        datastore.delete(datastore.createQuery(PostEntity.class).field(PostEntity.Fields.id).equal(id), WriteConcern.UNACKNOWLEDGED);
    }

    @Override
    public List<PostEntity> findHotPostByIds(List<String> hotIds) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.enable).equal(true);
        query.field(PostEntity.Fields.id).hasAnyOf(new HashSet<>(hotIds));
        return query.asList();
    }

    @Override
    public long count() {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.enable).equal(true);
        query.retrievedFields(true, PostEntity.Fields.id);
        return query.countAll();
    }

    @Override
    public List<PostEntity> getRecentlyPublished() {
        Query<PostEntity> query = createQuery();
        query.field(PostEntity.Fields.enable).equal(true);
        query.order("-" + PostEntity.Fields.id);
        return query.limit(10).asList();
    }

    @Override
    public PageResult<PostEntity> findPostByTagId(String id, Page page) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);

        query.field(PostEntity.Fields.deleted).equal(false);

        query.order("-" + PostEntity.Fields.lastModifiedTime);//添加排序
        if (page.getPageNumber() > 0) {
            query.offset((page.getPageNumber() - 1) * page.getPageSize()).limit(page.getPageSize());
        }
        query.field(PostEntity.Fields.topicId).equal(id);
        query.field(PostEntity.Fields.enable).equal(true);
        long totalNum = query.countAll();
        List<PostEntity> entities = query.asList();
        PageResult<PostEntity> ret = new PageResult<>();
        ret.setDataList(entities);
        ret.setTotal(totalNum);
        return ret;
    }

    @Override
    public PostEntity incPros(String id) {
        Query<PostEntity> query = createQuery();
        query.field(PostEntity.Fields.id).equal(id);
        UpdateOperations operations = createUpdateOperations();
        operations.inc(PostEntity.Fields.pros);
        return datastore.findAndModify(query, operations);
    }

    @Override
    public Long findPublishTotal() {
        Query<PostEntity> query = createQuery();
        query.field(PostEntity.Fields.enable).equal(true);
        return query.countAll();
    }

    @Override
    public Long findTodayPublishTotal() {
        Query<PostEntity> query = createQuery();
        query.field(PostEntity.Fields.enable).equal(true);
        query.field(PostEntity.Fields.lastModifiedTime).greaterThanOrEq(DateUtils.getInstance().getCurrentYYYYMMHH());
        return query.countAll();
    }

    @Override
    public PostEntity incPros(String ename, String shamId) {
        Query<PostEntity> query = createQuery();
        query.field(PostEntity.Fields.ename).equal(ename);
        query.field(PostEntity.Fields.shamId).equal(shamId);
        UpdateOperations operations = createUpdateOperations();
        operations.inc(PostEntity.Fields.pros);
        return datastore.findAndModify(query, operations);
    }

    @Override
    public PostEntity enable(String ename, String shamId, boolean enable, String userId) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.ename).equal(ename);
        query.field(PostEntity.Fields.shamId).equal(shamId);
        UpdateOperations<PostEntity> updateOperations = datastore.createUpdateOperations(PostEntity.class);
        updateOperations.set(PostEntity.Fields.enable, enable);
        updateOperations.set(PostEntity.Fields.lastModifiedBy, userId);
        //updateOperations.set(PostEntity.Fields.lastModifiedTime, System.currentTimeMillis());
        return datastore.findAndModify(query, updateOperations);
    }

    @Override
    public PostEntity findByShamIdInternal(String ename, String shamId) {
        Query<PostEntity> query = datastore.createQuery(PostEntity.class);
        query.field(PostEntity.Fields.shamId).equal(shamId);
        query.field(PostEntity.Fields.ename).equal(ename);
        return query.get();
    }

}
