
package com.poc.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.poc.utility.Constants;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Override

	@SuppressWarnings("unchecked")
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();
		String userName;
		try {
			userName = tokenProvider.getUserNameFromToken(authToken);
		} catch (Exception e) {
			userName = null;
		}
		if (userName != null && !tokenProvider.isTokenExpired(authToken)) {
			Claims claims = tokenProvider.getAllClaimsFromToken(authToken);
			List<String> roles = claims.get(Constants.AUTHORITIES_KEY, List.class);
			List<SimpleGrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, userName,
					authorities);
			SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(userName, authorities));
			return Mono.just(auth);
		} else {
			return Mono.empty();
		}
	}
}
