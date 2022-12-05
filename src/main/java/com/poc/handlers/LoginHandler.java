
package com.poc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.form.LogInForm;
import com.poc.models.User;
import com.poc.repositories.UserRepository;
import com.poc.security.JwtTokenProvider;
import com.poc.utility.ApiResponse;
import com.poc.wrapper.LoginResponseWrapper;

import reactor.core.publisher.Mono;

@Component
public class LoginHandler {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;


    public LoginHandler(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



	
	public Mono<ServerResponse> signUp(ServerRequest request) {
		Mono<User> userMono = request.bodyToMono(User.class);
		return userMono.map(user -> {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return user;
		}).flatMap(user -> userRepository.findByUserName(user.getUserName())
				.flatMap(dbUser -> ServerResponse.badRequest()
						.body(BodyInserters.fromValue(new ApiResponse(400, "User already exist", null))))
				.switchIfEmpty(userRepository.save(user).flatMap(savedUser -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(savedUser)))));
	}
	
	
	
	public Mono<ServerResponse> login(ServerRequest request) {
		Mono<LogInForm> loginRequest = request.bodyToMono(LogInForm.class);
		return loginRequest.flatMap(login -> userRepository.findByUserName(login.getUserName()).flatMap(user -> {
			if (passwordEncoder.matches(login.getPassword(), user.getPassword()))
				return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(new LoginResponseWrapper(tokenProvider.createToken(user))));
			
			   return ServerResponse.badRequest().build();
			 
			 
		}).switchIfEmpty(ServerResponse.badRequest().build()));
	}
	 

}
