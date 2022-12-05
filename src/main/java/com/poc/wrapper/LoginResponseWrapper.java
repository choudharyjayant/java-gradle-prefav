package com.poc.wrapper;

public class LoginResponseWrapper {	
	
	public LoginResponseWrapper(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token;

}
