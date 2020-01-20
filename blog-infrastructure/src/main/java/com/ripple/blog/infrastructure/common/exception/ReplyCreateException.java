package com.ripple.blog.infrastructure.common.exception;

public class ReplyCreateException extends BlogBusinessException {

	private static final long serialVersionUID = 2379115314530833813L;

	public ReplyCreateException(BlogBusinessExceptionCode code) {
		super(code);
	}

	public ReplyCreateException(BlogBusinessExceptionCode code, String message) {
		super(code.getCode(), message);
	}
}