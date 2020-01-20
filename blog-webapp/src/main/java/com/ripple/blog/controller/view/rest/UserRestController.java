package com.ripple.blog.controller.view.rest;

import com.google.common.base.Strings;
import com.ijson.mongo.generator.util.ObjectId;
import com.ripple.blog.controller.BaseController;
import com.ripple.blog.domain.model.RegModel;
import com.ripple.blog.domain.model.Result;
import com.ripple.blog.domain.service.UserService;
import com.ripple.blog.infrastructure.common.model.AuthContext;
import com.ripple.blog.infrastructure.common.model.Constant;
import com.ripple.blog.infrastructure.common.util.DesUtil;
import com.ripple.blog.infrastructure.dao.entity.UserEntity;
import com.ripple.blog.util.EhcacheUtil;
import com.ripple.blog.util.PassportHelper;
import com.ripple.blog.util.VerifyCodeUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/user/rest")
public class UserRestController extends BaseController {

	@Autowired
	private UserService userService;

	private String varCodeKey = "regVerCode";
	private String varCodeTime = "regCodeTime";

	@PostMapping(value = "/login")
	public Result login(HttpSession session, HttpServletResponse response, @RequestBody AuthContext context) {

		AuthContext authContext = userService.login(context.getEname(), context.getPassword());

		String tokenId = ObjectId.getId();

		if (Objects.nonNull(authContext)) {
			EhcacheUtil.getInstance().put(Constant.loginUserCacheKey, tokenId, authContext);
			session.setAttribute("authContext", authContext);
			Cookie cookie = new Cookie(PassportHelper.getInstance().getCookieName(), tokenId);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 30);
			response.addCookie(cookie);

			if (context.isRemember()) {
				EhcacheUtil.getInstance().put(Constant.remember, tokenId, authContext);
				Cookie rememberCookie = new Cookie(PassportHelper.getInstance().getRemCookieName(),
						DesUtil.encrypt(tokenId));
				rememberCookie.setPath("/");
				rememberCookie.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(rememberCookie);
			}
		}

		return Result.ok("登录成功!");
	}

	@PostMapping(value = "/logout")
	public Result logout(HttpSession session, HttpServletRequest request) {

		String cookieValue = PassportHelper.getInstance().getCurrCookie(request);
		if (Strings.isNullOrEmpty(cookieValue)) {
			return Result.error("登录证书已失效,自动退出");
		}
		AuthContext context = (AuthContext) EhcacheUtil.getInstance().get(Constant.loginUserCacheKey, cookieValue);
		if (Objects.isNull(context)) {
			return Result.error("登录证书已失效,自动退出");
		}
		session.removeAttribute("authContext");
		EhcacheUtil.getInstance().remove(Constant.loginUserCacheKey, cookieValue);
		EhcacheUtil.getInstance().remove(Constant.remember, cookieValue);

		return Result.ok("退出成功!");
	}

	@PostMapping(value = "/reg")
	public Result reg(HttpServletRequest request, HttpSession session, @RequestBody RegModel entity) {

		String verCode = (String) session.getAttribute(varCodeKey);
		Result result = VerifyCodeUtils.validImage(entity.getRegVerCode(), verCode, request, session, varCodeKey,
				varCodeTime);
		if (result.getCode() != 0) {
			return result;
		}

		String cookieValue = PassportHelper.getInstance().getCurrCookie(request);
		if (!Strings.isNullOrEmpty(cookieValue)) {
			AuthContext context = (AuthContext) EhcacheUtil.getInstance().get(Constant.loginUserCacheKey, cookieValue);
			if (Objects.nonNull(context)) {
				return Result.error("当前您已登录,不允许执行注册");
			}
		}
		String remCurrCookie = PassportHelper.getInstance().getRemCurrCookie(request);

		if (!Strings.isNullOrEmpty(remCurrCookie)) {
			cookieValue = DesUtil.decrypt(remCurrCookie);
			AuthContext context = (AuthContext) EhcacheUtil.getInstance().get(Constant.remember, cookieValue);
			if (Objects.nonNull(context)) {
				return Result.error("当前您已登录,不允许执行注册");
			}
		}

		userService.reg(UserEntity.create(entity.getEname(), entity.getCname(), entity.getEmail(), entity.getMobile(),
				entity.getPassword(), entity.getQq(), entity.getWechat(), entity.getWeibo()));

		return Result.ok("注册成功!");
	}

	@RequestMapping(value = "/reg/image", method = RequestMethod.GET)
	public void authImage(HttpServletResponse response, HttpSession session) throws IOException {
		generateVerification(response, session, varCodeKey, varCodeTime);
	}

}
