package com.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementDto {
	private String workUrn;
	private String entityUrn;
	private String type;
	private String slatemanifestUrn;
	private String text;
	private String createdBy;
	private String updatedBy;
	private String createdAt;
	private String updatedAt;
}
