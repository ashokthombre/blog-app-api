package com.blog.services;

import java.util.List;



import com.blog.playloads.UserDto;


public interface UserService {

public UserDto registerNewUser(UserDto userDto);	
	
 public UserDto createUser(UserDto user);
 
 public UserDto updateUser(UserDto user,Integer userId);
 
 public UserDto getUserById(Integer userId);
 
 public List<UserDto> getAllUsers();
 
  public void deleteUser(Integer userId);
}
