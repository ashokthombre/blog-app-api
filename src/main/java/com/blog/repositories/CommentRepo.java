package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entitis.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	
	
}
