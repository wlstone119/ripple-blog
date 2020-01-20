package com.ripple.blog.infrastructure.dao.entity;

import com.ijson.mongo.support.model.BaseEntity;
import com.ripple.blog.infrastructure.dao.model.AccessType;

import lombok.Data;
import lombok.ToString;
import org.mongodb.morphia.annotations.*;

@Data
@Entity(value = "Count", noClassnameStored = true)
@ToString(callSuper = true)
@Indexes({ @Index(name = "C_VIEWS", fields = { @Field(value = CountEntity.Fields.views), }) })
public class CountEntity extends BaseEntity {

	@Id
	private String id;

	@Property("views")
	private long views;

	@Property(Fields.accessType)
	private AccessType accessType;

	public static CountEntity create(PostEntity entity) {
		CountEntity countEntity = new CountEntity();
		countEntity.setId(entity.getId());
		countEntity.setAccessType(AccessType.blog);
		return countEntity;
	}

	public interface Fields {
		String id = "_id";
		String views = "views";
		String accessType = "accessType";
	}

}
