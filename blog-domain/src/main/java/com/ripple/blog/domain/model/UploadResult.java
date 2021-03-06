package com.ripple.blog.domain.model;

import com.google.common.base.Strings;
import lombok.Data;

@Data
public class UploadResult {

	private String fileName;
	private Integer uploaded = 1;
	private String url;
	private UploadErrorRst error;

	public static UploadResult create(String fileName, String url) {
		return create(fileName, url, null);
	}

	public static UploadResult create(String fileName, String url, String message) {
		UploadResult uploadResult = new UploadResult();
		uploadResult.setFileName(fileName);
		uploadResult.setUrl(url);
		if (!Strings.isNullOrEmpty(message)) {
			UploadErrorRst uploadErrorRst = new UploadErrorRst();
			uploadErrorRst.setMessage(message);
			uploadResult.setError(uploadErrorRst);
		}
		return uploadResult;
	}

}

@Data
class UploadErrorRst {

	private String message;

	private Integer number;

	public Integer getNumber() {
		return message.length();
	}

}