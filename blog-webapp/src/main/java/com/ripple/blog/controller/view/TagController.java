package com.ripple.blog.controller.view;

import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.controller.BaseController;
import com.ripple.blog.domain.model.HotTopic;
import com.ripple.blog.domain.model.Post;
import com.ripple.blog.infrastructure.common.exception.BlogNotFoundException;
import com.ripple.blog.infrastructure.common.util.Pageable;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;
import com.ripple.blog.infrastructure.dao.entity.TopicEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/tags")
public class TagController extends BaseController {

	@RequestMapping("/")
	public ModelAndView tags(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("view/tags.html");
		try {
			view.addObject("tags", HotTopic.getHotTopic(topicService.findAll()));
			view.addObject("tagActive", "active");
			addViewModelAndView(view);
			return view;
		} catch (BlogNotFoundException e) {
			view.setViewName("error/404.html");
			return view;
		}
	}

	@RequestMapping("/{id}")
	public ModelAndView tagById(@PathVariable("id") String id, Integer index, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("view/tag-post-list.html");
		try {
			Page page = new Page();

			if (Objects.isNull(index)) {
				index = 1;
			}
			page.setPageNumber(index);

			PageResult<PostEntity> result = postService.findPostByTagId(id, page);

			// view.addObject("tags", HotTopic.getHotTopic(topicService.findAll()));
			view.addObject("tagActive", "active");
			view.addObject("tagPostCount", result.getTotal());
			view.addObject("tagPost", Post.indexPost(result));
			view.addObject("id", id);

			TopicEntity topicEntity = topicService.find(id);

			if (Objects.nonNull(topicEntity)) {
				view.addObject("tagName", topicEntity.getTopicName());
			}

			view.addObject("page", new Pageable(((Long) result.getTotal()).intValue(), index));

			addViewModelAndView(view);

			return view;
		} catch (BlogNotFoundException e) {
			view.setViewName("error/404.html");
			return view;
		}
	}

	@RequestMapping("/{ename}/tags/{shamId}")
	public ModelAndView tagByShamId(@PathVariable("ename") String ename, @PathVariable("shamId") String shamId,
			Integer index, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("view/tag-post-list.html");
		TopicEntity topicEntity = topicService.findTopicByShamIdAndEname(ename, shamId);
		if (Objects.isNull(topicEntity)) {
			view.setViewName("error/404.html");
			return view;
		}

		String id = topicEntity.getId();
		try {
			Page page = new Page();

			if (Objects.isNull(index)) {
				index = 1;
			}
			page.setPageNumber(index);

			PageResult<PostEntity> result = postService.findPostByTagId(id, page);

			// view.addObject("tags", HotTopic.getHotTopic(topicService.findAll()));
			view.addObject("tagActive", "active");
			view.addObject("tagPostCount", result.getTotal());
			view.addObject("tagPost", Post.indexPost(result));
			view.addObject("id", id);
			view.addObject("tagName", topicEntity.getTopicName());
			view.addObject("page", new Pageable(((Long) result.getTotal()).intValue(), index));
			addViewModelAndView(view);
			return view;
		} catch (BlogNotFoundException e) {
			view.setViewName("error/404.html");
			return view;
		}
	}
}
