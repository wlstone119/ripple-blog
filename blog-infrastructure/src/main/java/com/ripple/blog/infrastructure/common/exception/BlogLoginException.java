package com.ripple.blog.infrastructure.common.exception;

public class BlogLoginException extends BlogBusinessException {

	private static final long serialVersionUID = -5847017662394345957L;

	public BlogLoginException(BlogBusinessExceptionCode code) {
		super(code);
	}
}
