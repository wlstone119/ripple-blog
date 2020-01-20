package com.ripple.blog.domain.model;

import lombok.Data;

@Data
public class ConsoleData {
	private Long publishTotal;
	private Long readTotal;
	private Long commentTotal;
	private Long todayPublishTotal;

	public static ConsoleData create(Long publishTotal, Long readTotal, Long commentTotal, Long todayPublishTotal) {

		ConsoleData consoleData = new ConsoleData();

		consoleData.setCommentTotal(commentTotal);
		consoleData.setPublishTotal(publishTotal);
		consoleData.setReadTotal(readTotal);
		consoleData.setTodayPublishTotal(todayPublishTotal);

		return consoleData;
	}
}
