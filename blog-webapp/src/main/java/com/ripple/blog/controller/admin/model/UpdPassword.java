package com.ripple.blog.controller.admin.model;

import lombok.Data;

@Data
public class UpdPassword {

	private String id;
	private String oldPwd;
	private String newPwd;
	private String againPwd;
	private String pwdVerCode;
}
