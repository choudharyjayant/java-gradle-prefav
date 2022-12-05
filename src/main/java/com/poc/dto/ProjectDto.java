package com.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private String distributableUrn;
    private String entityUrn;
	private String title;
	private String author;
	private String createdBy;
	private String createdAt;
	private String updatedBy;
	private String updatedAt;

}