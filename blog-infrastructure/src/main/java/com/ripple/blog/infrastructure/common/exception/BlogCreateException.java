package com.ripple.blog.infrastructure.common.exception;

public class BlogCreateException extends BlogBusinessException {

	private static final long serialVersionUID = 1980530890727606688L;

	public BlogCreateException(BlogBusinessExceptionCode code) {
		super(code);
	}
}