package com.ripple.blog.infrastructure.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ripple.blog.infrastructure.dao.entity.CountEntity;

public interface CountDao {

	CountEntity create(CountEntity entity);

	CountEntity findCountById(String id);

	Map<String, Long> findCountByIds(Set<String> ids);

	CountEntity createOrUpdate(CountEntity entity);

	CountEntity inc(CountEntity entity);

	List<CountEntity> findHot();

}
