package com.blog.services.impl;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entitis.Category;
import com.blog.entitis.Post;
import com.blog.entitis.User;
import com.blog.exceptions.ResourceNotFoundException;

import com.blog.playloads.PostDto;
import com.blog.playloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId ) {
		
       User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Catrgory","id", categoryId));
         
         
       
		
		    Post post= this.modelMapper.map(postDto, Post.class);
		     post.setImageName("default.png");
		     post.setAddDate(new Date());
		     post.setUser(user);
		     post.setCategory(category);
		     
		     Post savedPost=this.postRepo.save(post);
		     
		     return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
     Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
     
	   post.setContent(postDto.getContent());
	   post.setTitle(postDto.getTitle());
	   post.setImageName(postDto.getImageName());
	  

	   
	  Post updatedpost= this.postRepo.save(post);
   
   
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		 Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        
		 this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int paseSize,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?sort=Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//		if(sortDir.equalsIgnoreCase("asc"))
//		{
//			sort=Sort.by(sortBy).ascending();
//		}
//		else
//		{
//			sort=Sort.by(sortBy).descending();
//		}
		
		 Pageable p=PageRequest.of(pageNumber, paseSize,sort);
		 
		Page<Post>pagePost= this.postRepo.findAll(p);
		 
		 List<Post> post=pagePost.getContent();
		 List<PostDto> postDtos=post.stream().map(posts-> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		 
		 PostResponse postResponse=new PostResponse();
		 postResponse.setContent(postDtos);
		 postResponse.setPageNumber(pagePost.getNumber());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalElement(pagePost.getTotalElements());
		 
		 postResponse.setTotalPages(pagePost.getTotalPages());
		 
		 postResponse.setLastPage(pagePost.isLast());
		 
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		   Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		 List<Post> post=this.postRepo.findByCategory(category);
		 
		List<PostDto> postdtos= post.stream().map(posts->this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
		return postdtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
	   User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id", userId));	
	
	   List<Post>post= this.postRepo.findByUser(user);
	   
	   List<PostDto> postDto=post.stream().map(posts->this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
	   
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	
     List<Post> post=this.postRepo.findByTitleContaining(keyword);
		
      List<PostDto>postDto= post.stream().map(posts->this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
		
		return postDto;
	}
//	private Post dtoToPost(PostDto postDto)
//	{
//		Post post=this.modelMapper.map(postDto,Post.class);
//		
//		
//		
////		user.setId(userDto.getId());
////		user.setName(userDto.getName());
////		user.setEmail(userDto.getEmail());
////		user.setAbout(userDto.getAbout());
////		user.setPassword(userDto.getPassword());
//		
//		return post;
//		
//	}
//	
//	private PostDto postToDto(Post post)
//	{
//		
//	 PostDto postDto=this.modelMapper.map(post, PostDto.class);
//		
//	  return postDto;
//	}

}
