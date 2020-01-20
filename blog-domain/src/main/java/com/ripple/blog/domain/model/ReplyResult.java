package com.ripple.blog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.ripple.blog.infrastructure.common.util.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyResult {
	
	private List<Reply> reply;
	
	private Pageable pageable;
	
}
