package com.ripple.blog.infrastructure.dao.impl;

import com.google.common.base.Strings;
import com.ijson.mongo.generator.util.ObjectId;
import com.ijson.mongo.support.AbstractDao;
import com.ripple.blog.infrastructure.dao.FileUploadDao;
import com.ripple.blog.infrastructure.dao.entity.FileUploadEntity;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.stereotype.Component;

@Component
public class FileUploadDaoImpl extends AbstractDao<FileUploadEntity> implements FileUploadDao {
	@Override
	public FileUploadEntity createOrUpdate(FileUploadEntity entity) {
		if (!Strings.isNullOrEmpty(entity.getId())) {
			entity = findAndModify(entity);
		} else {
			entity.setId(ObjectId.getId());
			datastore.save(entity);
			return entity;
		}
		return entity;
	}

	@Deprecated
	private FileUploadEntity findAndModify(FileUploadEntity entity) {
		Query<FileUploadEntity> query = createQuery();
		query.field(FileUploadEntity.Fields.id).equal(entity.getId());
		UpdateOperations operations = createUpdateOperations();

		return datastore.findAndModify(query, operations);
	}

	@Override
	public FileUploadEntity findDataByMd5(String md5) {
		Query<FileUploadEntity> query = datastore.createQuery(FileUploadEntity.class);
		query.field(FileUploadEntity.Fields.md5).equal(md5);
		return query.get();
	}
}
