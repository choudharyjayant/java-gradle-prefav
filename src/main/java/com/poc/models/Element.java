package com.poc.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("element Data")
public class Element {

	@Id
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
