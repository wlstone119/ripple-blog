package com.ripple.blog.infrastructure.common.model;

import lombok.Data;

@Data
public class Permission {

	private String ename;
	private String cname;
	private String path;
	// menu,action
	private PermissionType type;
	private String fatherEname;
	private boolean clickable;

	public static Permission create(String ename, String cname, String path, PermissionType type, String fatherEname,
			boolean clickable) {
		Permission permission = new Permission();
		permission.setEname(ename);
		permission.setCname(cname);
		permission.setPath(path);
		permission.setType(type);
		permission.setFatherEname(fatherEname);
		permission.setClickable(clickable);
		return permission;
	}
}
