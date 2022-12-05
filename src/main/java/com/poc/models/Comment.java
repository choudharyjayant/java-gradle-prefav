package com.poc.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Document("comment-data")
public class Comment {

	@Id
	private String workUrn;
	private String entityUrn;
	private String elementWorkUrn;
	private String slateManifestUrn;
	private String text;
	private String userId;
	private String createdBy;
	private String createdAt;
	private String updatedBy;
	private String updatedAt;
}
