package com.blog.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.blog.playloads.ApiResponse;
import com.blog.playloads.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
    
	
	//POST-create usre
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		
	UserDto creUserDto=	this.userService.createUser(userDto);
		
	return new ResponseEntity<UserDto>(creUserDto,HttpStatus.CREATED);
	}
	
	//update user
    
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") int userId)
	{
		
		
	UserDto updatedUser=this.userService.updateUser(userDto,userId);
	
	return new ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
		
	}
	//ADMIN
	
	
	//delete user
	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}") 	
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int userId)
	{
		
        this.userService.deleteUser(userId);
        
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfuly",true),HttpStatus.OK);
		
	}
    
    //get user by id
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") int userId)
    {
    	
    	
    	UserDto userDto=this.userService.getUserById(userId);
    	
    	return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
    	
    	
    }
    
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
    	
     return ResponseEntity.ok(this.userService.getAllUsers());
    
    	
    }
	
	
	
}
