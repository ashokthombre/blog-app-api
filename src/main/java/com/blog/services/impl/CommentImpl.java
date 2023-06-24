package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entitis.Comment;
import com.blog.entitis.Post;
import com.blog.entitis.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.playloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.CommentService;

@Service
public class CommentImpl implements CommentService {
     @Autowired
	 private CommentRepo commentRepo;
	
     @Autowired
     private PostRepo postRepo;
     
     @Autowired
     private UserRepo userRepo;
     
     @Autowired
     private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
		
		  User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		  
		  Post post= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		   
		 Comment comment=this.modelMapper.map(commentDto,Comment.class);
		 
		 comment.setPost(post);
		 comment.setUser(user);
		 
		Comment savedComment= this.commentRepo.save(comment);
		
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		
		
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
		
		this.commentRepo.delete(comment);
	}

}
