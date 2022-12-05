package com.poc.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentDTO {
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
