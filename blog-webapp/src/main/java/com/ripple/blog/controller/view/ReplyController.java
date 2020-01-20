package com.ripple.blog.controller.view;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.controller.BaseController;
import com.ripple.blog.domain.model.Reply;
import com.ripple.blog.domain.model.ReplyResult;
import com.ripple.blog.domain.model.Result;
import com.ripple.blog.domain.service.ReplyService;
import com.ripple.blog.infrastructure.common.exception.BlogBusinessExceptionCode;
import com.ripple.blog.infrastructure.common.exception.ReplyCreateException;
import com.ripple.blog.infrastructure.common.model.AuthContext;
import com.ripple.blog.infrastructure.common.util.Pageable;
import com.ripple.blog.infrastructure.dao.entity.ReplyEntity;
import com.ripple.blog.infrastructure.dao.query.ReplyQuery;
import com.ripple.blog.util.VerifyCodeUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * desc: version: 6.7 Created by cuiyongxu on 2019/8/25 1:58 AM
 */
@Slf4j
@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController {

	@Autowired
	private ReplyService replyService;

	private String replyCodeKey = "replyCode";
	private String replyCodeTime = "replyCodeTime";

	@PostMapping("/{ename}/replys/{shamId}")
	@ResponseBody
	public ReplyResult getReplyList(@PathVariable("ename") String ename, @PathVariable("shamId") String shamId,
			Integer index) throws Exception {

		Page page = new Page();
		if (Objects.isNull(index)) {
			index = 1;
		}
		page.setPageNumber(index);

		ReplyQuery replyQuery = new ReplyQuery();
		replyQuery.setEname(ename);
		replyQuery.setShamId(shamId);

		PageResult<ReplyEntity> result = replyService.find(replyQuery, page);
		List<Reply> replies = result.getDataList().stream().map(Reply::formReply).collect(Collectors.toList());

		return new ReplyResult(replies, new Pageable(((Long) result.getTotal()).intValue(), index));
	}

	@RequestMapping("/{ename}/{shamId}/save")
	@ResponseBody
	public Reply getReplyList(@PathVariable("ename") String ename, @PathVariable("shamId") String shamId,
			@RequestBody Reply reply, HttpSession session, HttpServletRequest request) throws Exception {

		String verCode = (String) session.getAttribute(replyCodeKey);
		Result result = VerifyCodeUtils.validImage(reply.getReplyCode(), verCode, request, session, replyCodeKey,
				replyCodeTime);

		if (result.getCode() != 0) {
			throw new ReplyCreateException(BlogBusinessExceptionCode.CAPTCHA_ERROR_OR_NOT_PRESENT, result.getMessage());
		}

		AuthContext context = getContext(request);

		if (Objects.isNull(context)) {
			log.info("创建评论时,未获取到用户信息");
			throw new ReplyCreateException(BlogBusinessExceptionCode.USER_INFORMATION_ACQUISITION_FAILED);
		}

		return Reply.formReply(
				replyService.save(reply.getContent(), shamId, ename, Reply.formReplyEntity(reply, request, context)));
	}

	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public void authImage(HttpServletResponse response, HttpSession session) throws IOException {
		generateVerification(response, session, replyCodeKey, replyCodeTime);
	}

}
