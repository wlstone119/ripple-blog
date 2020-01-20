package com.ripple.blog.infrastructure.common.exception;

public class BlogNotFoundException extends BlogBusinessException {

	private static final long serialVersionUID = 2338089840411397517L;

	public BlogNotFoundException(BlogBusinessExceptionCode code) {
		super(code);
	}
}