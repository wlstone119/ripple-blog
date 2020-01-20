package com.ripple.blog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ripple.blog.infrastructure.manager.ViewSyncManager;
import com.ripple.blog.interceptor.AccessStatisticInterceptor;
import com.ripple.blog.interceptor.LoginInterceptor;
import com.ripple.blog.interceptor.RememberLoginCacheInterceptor;

@Configuration
public class DefaultView implements WebMvcConfigurer {

	@Autowired
	private ViewSyncManager viewSyncManager;

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("view/index.html");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RememberLoginCacheInterceptor()).addPathPatterns("/**");

		// 可添加多个
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**", "/post/**", "/user/edit/**")
				.excludePathPatterns("/user/edit/image");

		registry.addWebRequestInterceptor(new AccessStatisticInterceptor(viewSyncManager)).addPathPatterns("/index",
				"/detail/**", "/tags", "/tags/**", "/");
	}
}
