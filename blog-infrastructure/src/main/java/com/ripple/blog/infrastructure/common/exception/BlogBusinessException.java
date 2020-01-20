package com.ripple.blog.infrastructure.common.exception;

public class BlogBusinessException extends BlogRuntimeException {

	private static final long serialVersionUID = -5580763262349522120L;

	public BlogBusinessException(BlogBusinessExceptionCode code, String extensionMessage) {
		super(code.getCode(), extensionMessage);
	}

	public BlogBusinessException(Integer code, String extensionMessage) {
		super(code, extensionMessage);
	}

	public BlogBusinessException(BlogBusinessExceptionCode code) {
		super(code.getCode(), code.getMessage());
	}

}