package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entitis.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	  public Optional<User> findByEmail(String email);
	
}
