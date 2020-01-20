package com.ripple.blog.domain.service;

import com.ripple.blog.infrastructure.dao.entity.FileUploadEntity;

public interface FileUploadService {

	FileUploadEntity create(FileUploadEntity entity);

	FileUploadEntity findDataByMd5(String md5);

}
