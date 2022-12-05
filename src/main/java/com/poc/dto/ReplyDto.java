package com.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {

	private String workUrn;
	private String entityUrn;
	private String commentUrn;
	private String text;
	private String userId;
	private String createdBy;
	private String createdAt;
	private String updatedBy;
	private String updatedAt;

}
