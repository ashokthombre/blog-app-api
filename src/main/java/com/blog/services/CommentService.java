package com.blog.services;

import com.blog.playloads.CommentDto;

public interface CommentService {

	CommentDto createComment (CommentDto commentDto,int postId,int userId);
	
	void deleteComment(int commentId);
}
