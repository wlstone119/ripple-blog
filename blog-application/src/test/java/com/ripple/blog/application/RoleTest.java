package com.ripple.blog.application;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.ripple.blog.domain.service.RoleService;
import com.ripple.blog.infrastructure.common.model.Permission;
import com.ripple.blog.infrastructure.dao.entity.RoleEntity;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RoleTest {

	@Autowired
	private RoleService roleServiceImpl;

	@Test
	public void test() {
		
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setId("5e2594abe6105c6bc35a0e56");
		roleEntity.setEname("common");
		roleEntity.setCname("普通用户");
		roleEntity.setStatus("0");
		roleEntity.setEnable(true);
		roleEntity.setDeleted(false);
		
		roleEntity.setPermission(buildPermission());
		
		roleServiceImpl.create(roleEntity);
	}
	
	private List<Permission> buildPermission(){
		String json = "[\n" + 
				"        {\n" + 
				"            \"ename\": \"admin_console_page\",\n" + 
				"            \"cname\": \"首页\",\n" + 
				"            \"path\": \"/admin/console/page\",\n" + 
				"            \"type\": \"menu\",\n" + 
				"            \"fatherEname\": \"root\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"admin_post_root\",\n" + 
				"            \"cname\": \"文章\",\n" + 
				"            \"path\": \"/admin/post/root\",\n" + 
				"            \"type\": \"menu\",\n" + 
				"            \"fatherEname\": \"root\",\n" + 
				"            \"clickable\": false\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"admin_post_list_page\",\n" + 
				"            \"cname\": \"文章列表\",\n" + 
				"            \"path\": \"/admin/post/list/page\",\n" + 
				"            \"type\": \"menu\",\n" + 
				"            \"fatherEname\": \"admin_post_root\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"admin_post_add_page\",\n" + 
				"            \"cname\": \"新建/编辑文章\",\n" + 
				"            \"path\": \"/admin/post/add/page\",\n" + 
				"            \"type\": \"menu\",\n" + 
				"            \"fatherEname\": \"admin_post_root\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"admin_system_root\",\n" + 
				"            \"cname\": \"系统设置\",\n" + 
				"            \"path\": \"/admin/system/root\",\n" + 
				"            \"type\": \"menu\",\n" + 
				"            \"fatherEname\": \"root\",\n" + 
				"            \"clickable\": false\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"admin_i_config_page\",\n" + 
				"            \"cname\": \"我的设置\",\n" + 
				"            \"path\": \"/admin/i/config/page\",\n" + 
				"            \"type\": \"menu\",\n" + 
				"            \"fatherEname\": \"admin_system_root\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"admin_edit_ename_shamId_page\",\n" + 
				"            \"cname\": \"新建/编辑文章\",\n" + 
				"            \"path\": \"/admin/edit/*/*/page\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"post_create\",\n" + 
				"            \"cname\": \"博客创建\",\n" + 
				"            \"path\": \"/post/create\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"post_enable_ename_shamId\",\n" + 
				"            \"cname\": \"启用/禁用\",\n" + 
				"            \"path\": \"/post/enable/*/*\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"post_delete_ename_shamId\",\n" + 
				"            \"cname\": \"删除博文\",\n" + 
				"            \"path\": \"/post/delete/*/*\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"post_upload\",\n" + 
				"            \"cname\": \"博文附件上传\",\n" + 
				"            \"path\": \"/post/upload\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"post_list\",\n" + 
				"            \"cname\": \"rest 博文列表\",\n" + 
				"            \"path\": \"/post/list\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"user_edit_webset\",\n" + 
				"            \"cname\": \"编辑网站信息\",\n" + 
				"            \"path\": \"/user/edit/webset\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"user_edit_base\",\n" + 
				"            \"cname\": \"编辑基础信息\",\n" + 
				"            \"path\": \"/user/edit/base\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"user_edit_contact\",\n" + 
				"            \"cname\": \"编辑用户常用联系方式\",\n" + 
				"            \"path\": \"/user/edit/contact\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"ename\": \"user_edit_password\",\n" + 
				"            \"cname\": \"编辑用户密码\",\n" + 
				"            \"path\": \"/user/edit/password\",\n" + 
				"            \"type\": \"action\",\n" + 
				"            \"fatherEname\": \"\",\n" + 
				"            \"clickable\": true\n" + 
				"        }\n" + 
				"    ]";
		
		return (List<Permission>) JSON.parseArray(json, Permission.class);
	}

}
