package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entitis.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

	
}
