package com.ripple.blog.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ripple.blog.controller.BaseController;
import com.ripple.blog.domain.model.Post;
import com.ripple.blog.infrastructure.common.exception.BlogNotFoundException;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;

@Slf4j
@Controller()
@RequestMapping("/article")
public class ArticleController extends BaseController {

	/**
	 * @param ename
	 * @param shamId
	 * @return
	 */
	@RequestMapping("/{ename}/details/{shamId}")
	public ModelAndView details(@PathVariable("ename") String ename, @PathVariable("shamId") String shamId) {
		ModelAndView view = new ModelAndView("view/detail.html");
		try {
			PostEntity entity = postService.findByShamId(ename, shamId);
			view.addObject("data", Post.create(entity));
			addViewModelAndView(view);
			return view;
		} catch (BlogNotFoundException e) {
			view.setViewName("error/404.html");
			return view;
		}
	}
}
