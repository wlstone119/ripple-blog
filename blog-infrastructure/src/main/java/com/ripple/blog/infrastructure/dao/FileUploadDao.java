package com.ripple.blog.infrastructure.dao;

import com.ripple.blog.infrastructure.dao.entity.FileUploadEntity;

public interface FileUploadDao {

	FileUploadEntity createOrUpdate(FileUploadEntity entity);

	FileUploadEntity findDataByMd5(String md5);

}
