package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiException;
import com.blog.playloads.JwtAuthRequest;
import com.blog.playloads.JwtAuthResponse;
import com.blog.playloads.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request,HttpSession session) throws Exception
	{
		System.err.println(request.getUsername());
		
		System.err.println(request.getPassword());

		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
	   
		String token=this.jwtTokenHelper.generateToken(userDetails);
		
		session.setAttribute("token",token);
		
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
		
	
	}

	private void authenticate(String username, String password) throws Exception {
		
		    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		   
		    	try 
		    	{
		    		this.authenticationManager.authenticate(authenticationToken);	
		    		
				} catch (BadCredentialsException e) {
					
					System.err.println("Invalid Deatils");
					throw new ApiException("Invalid username or password");
				}
		    	
		    	
		    	
		 
		    
	}
	
	//Register new user
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.OK);
	}
	
	
	
	
	
}
