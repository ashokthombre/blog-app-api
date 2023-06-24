package com.blog.services;

import java.util.List;


import com.blog.playloads.PostDto;
import com.blog.playloads.PostResponse;

public interface PostService {
	
	//create
	
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId );
	
	//update
	
	public PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	
	 public void deletePost(Integer postId);
	 
	 //get all post
	 
	 PostResponse getAllPost(int pageNumber,int paseSize,String sortBy,String sortDir);
	 
	//get single post
	 
	 public PostDto getPostById(Integer postId);
	 
	 //get all post bycategory
	 
	 public List<PostDto> getPostByCategory(Integer categoryId);
	 
	 
	 //get all post by user
	 
	 public List<PostDto> getPostByUser(Integer userId);
	 
	 //search post
	 
	 List<PostDto> searchPosts(String keyword);
	 
	 
	 

}
