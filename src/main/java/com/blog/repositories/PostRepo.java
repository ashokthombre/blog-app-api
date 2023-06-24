package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entitis.Category;
import com.blog.entitis.Post;
import com.blog.entitis.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
  
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
	
}
