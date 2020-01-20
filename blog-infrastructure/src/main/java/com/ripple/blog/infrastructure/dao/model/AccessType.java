package com.ripple.blog.infrastructure.dao.model;

public enum AccessType {
	blog("博客"), webSite("网站");

	String desc;

	AccessType(String desc) {
		this.desc = desc;
	}
}
