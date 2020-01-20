package com.ripple.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ripple.blog.domain.service.FileUploadService;
import com.ripple.blog.infrastructure.dao.FileUploadDao;
import com.ripple.blog.infrastructure.dao.entity.FileUploadEntity;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private FileUploadDao fileUploadDao;

	@Override
	public FileUploadEntity create(FileUploadEntity entity) {
		return fileUploadDao.createOrUpdate(entity);
	}

	@Override
	public FileUploadEntity findDataByMd5(String md5) {
		return fileUploadDao.findDataByMd5(md5);
	}
}
