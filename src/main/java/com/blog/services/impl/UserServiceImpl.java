package com.blog.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstant;
import com.blog.entitis.Role;
import com.blog.entitis.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.playloads.UserDto;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
   
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
	     
		    User user=this.dtoToUser(userDto);
		
		User savedUser=this.userRepo.save(user);
		return this.usertoDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		 
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		
		User userUpdate=this.userRepo.save(user);
		
		
		
		return this.usertoDto(userUpdate);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		
		 return this.usertoDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
	       List<User>users=this.userRepo.findAll();
		
           List<UserDto> userDto=users.stream().map(user-> this.usertoDto(user)).collect(Collectors.toList());
	
		   return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		
    User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
    this.userRepo.delete(user);
	}
	
	
	private User dtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto,User.class);
		
		
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
		
	}
	
	public UserDto usertoDto(User user)
	{
		
		UserDto userDto =this.modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto,User.class);
		
		  user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		  Role role=this.roleRepo.findById(AppConstant.NORMAL_USER).get();
		  
		  user.getRoles().add(role);
		  
		 User newUser=this.userRepo.save(user);
		  
		return this.modelMapper.map(newUser, UserDto.class);
	}
	

}
