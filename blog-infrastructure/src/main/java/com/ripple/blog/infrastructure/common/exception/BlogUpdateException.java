package com.ripple.blog.infrastructure.common.exception;

public class BlogUpdateException extends BlogBusinessException {

	private static final long serialVersionUID = -1629665845955370118L;

	public BlogUpdateException(BlogBusinessExceptionCode code) {
		super(code);
	}
}