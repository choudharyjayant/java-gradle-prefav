package com.poc.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("users-data")
public class User {

	@Id
	private String id;
	private String name;

	@Indexed
	private String userName;
	private String password;

	@JsonIgnoreProperties
	private List<Role> roles;

}
