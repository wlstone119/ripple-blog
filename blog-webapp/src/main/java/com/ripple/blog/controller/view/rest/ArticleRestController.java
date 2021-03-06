package com.ripple.blog.controller.view.rest;

import com.google.common.base.Strings;
import com.ripple.blog.controller.BaseController;
import com.ripple.blog.domain.model.Result;
import com.ripple.blog.infrastructure.common.exception.BlogBusinessExceptionCode;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;
import com.ripple.blog.util.PassportHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/article/rest")
public class ArticleRestController extends BaseController {

	@PostMapping(value = "/{ename}/praise/{shamId}")
	public Result praise(@PathVariable("ename") String ename, @PathVariable("shamId") String shamId,
			HttpServletRequest request, HttpServletResponse response) {
		String cookieName = ename + "_" + shamId + "_praise";
		String praise = PassportHelper.getInstance().getCookie(request, cookieName);
		if (!Strings.isNullOrEmpty(praise)) {
			return Result.error(BlogBusinessExceptionCode.YOU_ALREADY_SUPPORTED_IT);
		}

		PostEntity entity = postService.incPros(ename, shamId);
		if (Objects.nonNull(entity)) {
			String time = System.currentTimeMillis() + "";
			Cookie cookie = new Cookie(cookieName, time);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(cookie);
		}
		return Result.ok("感谢您的支持!");
	}

}
