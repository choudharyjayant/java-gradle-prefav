package com.poc.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class AuthenticatedUser implements Authentication {
	
	private static final long serialVersionUID = 6861381095901879822L;
	private String userId;
    private boolean authenticated = true;
    private List<SimpleGrantedAuthority> roles;

	@Override
	public String getName() {
		return this.userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}
	
	

	@Override
	public Object getCredentials() {
		return this.userId;
	}
	
	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.userId;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated=isAuthenticated;
		
	}

	public AuthenticatedUser(String userName, List<SimpleGrantedAuthority> roles) {
		this.userId=userName;
		this.roles=roles;
	}
	

}
