package com.ripple.blog.infrastructure.dao.entity;

import com.ijson.mongo.support.model.BaseEntity;
import com.ripple.blog.infrastructure.common.model.AuthContext;
import com.ripple.blog.infrastructure.common.model.Constant;

import lombok.Data;
import lombok.ToString;
import org.mongodb.morphia.annotations.*;

@Data
@Entity(value = "Topic", noClassnameStored = true)
@ToString(callSuper = true)
@Indexes({ @Index(name = "T_TICN", fields = { @Field(value = TopicEntity.Fields.topicName) }),

		@Index(name = "T_ENAME_SHAMID", fields = { @Field(value = TopicEntity.Fields.ename),
				@Field(value = TopicEntity.Fields.shamId) }) })
public class TopicEntity extends BaseEntity {

	@Id
	private String id;

	@Property(Fields.userId)
	private String userId;

	@Property("ename")
	private String ename;

	@Property("shamId")
	private String shamId;

	@Property(Fields.moduleId)
	private String moduleId;

	@Property("topicName")
	private String topicName;

	@Property(Fields.postCount)
	private long postCount;

	@Property(Fields.lastModifiedBy)
	private String lastModifiedBy;

	@Property(Fields.deleted)
	private Boolean deleted;

	@Property(Fields.enable)
	private boolean enable;

	@Property(Fields.createdBy)
	private String createdBy;

	@Property(Fields.createTime)
	private long createTime;

	public static TopicEntity create(String topicName, AuthContext context) {
		TopicEntity topicEntity = new TopicEntity();
		topicEntity.setDeleted(false);
		topicEntity.setEnable(true);
		topicEntity.setCreatedBy(context.getId());
		topicEntity.setCreateTime(System.currentTimeMillis());
		topicEntity.setLastModifiedBy(context.getId());
		topicEntity.setTopicName(topicName.toLowerCase().trim());
		topicEntity.setUserId(context.getId());
		topicEntity.setModuleId(Constant.defaultTopicModel);
		topicEntity.setPostCount(1L);
		topicEntity.setEname(context.getEname());
		return topicEntity;
	}

	public interface Fields {
		String id = "_id";
		String userId = "userId";
		String moduleId = "moduleId";
		String postCount = "postCount";
		String createdBy = "createdBy";
		String createTime = "createTime";
		String enable = "enable";
		String deleted = "deleted";
		String lastModifiedBy = "lastModifiedBy";
		String topicName = "topicName";
		String ename = "ename";
		String shamId = "shamId";

	}
}
