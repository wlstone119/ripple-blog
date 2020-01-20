package com.ripple.blog.infrastructure.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ripple.blog.infrastructure.common.model.Constant;
import com.ripple.blog.infrastructure.dao.CountDao;
import com.ripple.blog.infrastructure.dao.entity.CountEntity;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;
import com.ripple.blog.infrastructure.dao.model.AccessType;

import java.util.Objects;

@Slf4j
@Component
public class ViewSyncManager {

	@Autowired
	private CountDao countDao;

	// 异步保存查看次数
	@Async
	public void syncViewBlog(PostEntity entity) {
		CountEntity countEntity = countDao.findCountById(entity.getId());
		if (Objects.isNull(countEntity)) {
			countEntity = CountEntity.create(entity);
			countDao.create(countEntity);
		} else {
			countEntity = countDao.createOrUpdate(countEntity);
		}
		log.info("博文:[{}]查看次数", countEntity.getViews());
	}

	@Async
	public void syncWebSite() {
		CountEntity countEntity = new CountEntity();
		countEntity.setAccessType(AccessType.webSite);
		countEntity.setId(Constant.WEB_SITE_COUNT);
		CountEntity countById = countDao.findCountById(Constant.WEB_SITE_COUNT);
		if (countById == null) {
			countDao.create(countEntity);
		} else {
			countDao.inc(countEntity);
		}
	}

	@Async
	public void saveViewBlog(PostEntity entity) {
		CountEntity countEntity = CountEntity.create(entity);
		countEntity = countDao.create(countEntity);
		log.info("博文:[{}]查看次数", countEntity.getViews());
	}
}
