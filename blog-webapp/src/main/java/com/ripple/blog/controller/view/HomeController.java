package com.ripple.blog.controller.view;

import com.google.common.base.Strings;
import com.ijson.mongo.support.model.Page;
import com.ijson.mongo.support.model.PageResult;
import com.ripple.blog.controller.BaseController;
import com.ripple.blog.domain.model.Post;
import com.ripple.blog.infrastructure.common.util.Pageable;
import com.ripple.blog.infrastructure.dao.entity.PostEntity;
import com.ripple.blog.infrastructure.dao.query.PostQuery;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Controller
public class HomeController extends BaseController {

	@RequestMapping("/")
	public ModelAndView root(HttpServletRequest request) {
		String keyWord = request.getParameter("keyWord");
		String pageNumber = request.getParameter("index");
		int index = 1;
		if (!Strings.isNullOrEmpty(pageNumber)) {
			boolean number = NumberUtils.isNumber(pageNumber);
			if (number) {
				index = Integer.parseInt(pageNumber);
			}
		}
		return index(index, keyWord);
	}

	@RequestMapping("/index")
	public ModelAndView index(Integer index, String keyWord) {
		ModelAndView view = new ModelAndView("view/index.html");

		Page page = new Page();
		if (Objects.isNull(index)) {
			index = 1;
		}

		page.setPageNumber(index);

		PostQuery query = new PostQuery();
		if (!Strings.isNullOrEmpty(keyWord)) {
			query.setLikeTitle(true);
			query.setTitle(keyWord);
		}

		query.setEnable(true);

		PageResult<PostEntity> result = postService.find(query, page);

		view.addObject("total", result.getTotal());
		view.addObject("page", new Pageable(((Long) result.getTotal()).intValue(), index));
		view.addObject("dataList", Post.indexPost(result, keyWord));
		view.addObject("indexActive", "active");
		view.addObject("keyWord", keyWord);

		addViewModelAndView(view);

		return view;
	}

}
