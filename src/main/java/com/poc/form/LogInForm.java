package com.poc.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInForm {

	private String userName;
	
	//@NotBlank(message="{password.not.null}")
	private String password;
	
}
