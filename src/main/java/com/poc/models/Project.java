package com.poc.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document("project-db")
public class Project {
    
	@Id
    private String distributableUrn;
    private String entityUrn;
	private String title;
	private String author;
	private String createdBy;
	private String createdAt;
	private String updatedBy;
	private String updatedAt;
}