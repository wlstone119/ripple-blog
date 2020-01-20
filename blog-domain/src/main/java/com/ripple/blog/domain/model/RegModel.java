package com.ripple.blog.domain.model;

import lombok.Data;

@Data
public class RegModel {

	private String id;
	private String ename;
	private String cname;
	private String password;
	private String email;
	private String mobile;

	private String qq;
	private String wechat;
	private String weibo;
	private String regVerCode;

}
