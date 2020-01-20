package com.ripple.blog.domain.model;

import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.infrastructure.common.util.Pageable;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class DTable {
	// 当前页
	private int sEcho;
	// 总数
	private long iTotalRecords;
	// 筛选后总数
	private long iTotalDisplayRecords;
	// 返回的集合
	private List<Post> aaData;

	private long recordsTotal;
	private long recordsFiltered;

	public static DTable create(List<Post> posts, PageResult<PostEntity> result, int index) {
		if (Objects.isNull(result)) {
			return new DTable();
		}
		Pageable pageable = new Pageable(((Long) result.getTotal()).intValue(), index);

		DTable data = new DTable();
		data.setITotalDisplayRecords(result.getTotal());
		data.setITotalRecords(result.getTotal());
		data.setRecordsFiltered(result.getTotal());
		data.setRecordsTotal(result.getTotal());
		data.setAaData(posts);
		data.setSEcho(pageable.getCurrentPage());
		return data;
	}
}
