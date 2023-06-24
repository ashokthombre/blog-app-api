package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstant;
import com.blog.playloads.ApiResponse;
import com.blog.playloads.PostDto;
import com.blog.playloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController
{
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	//create post
    @PostMapping("/user/{userId}/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable int userId,@PathVariable int categoryId)
	{
		
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
    
    
    //updatPost
    
    @PutMapping("/user/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
    		@PathVariable int postId)
    {
    	
       PostDto updatePost= this.postService.updatePost(postDto, postId);    
    	
       return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    	
    }
    
    //delete post
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId)
    {
    	
    	this.postService.deletePost(postId);
    	return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Succesfully",true),HttpStatus.OK);
    	
    }
    
    //getAll posts
    
    @GetMapping("/user/posts")
    public ResponseEntity<PostResponse> getAllPost(
    		@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false ) int pageNumber,
    
              @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY, required=false) String sortBy,
            
            @RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR ,required = false) String sortDir)
    {
    	
    	
    	PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
    	
    	return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    	
    	
    	
    }
    
    //getpostByid
    
    @GetMapping("user/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId)
    {
    	
    	PostDto postDto=this.postService.getPostById(postId);
    	
    	return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    	
    	
    }
    
    
    //get post by category
    
    @GetMapping("/user/posts/category/{catId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable int catId)
    {
    	
    	List<PostDto>postDto=this.postService.getPostByCategory(catId);
    	
    	return new ResponseEntity<List<PostDto>>(postDto,HttpStatus.OK);
    	
    	
    	
    }
    
//    get post by user
    @GetMapping("/user/posts/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable int userId)
    {   
    	  List<PostDto> postDto= this.postService.getPostByUser(userId);
    	  
    	  return new ResponseEntity<List<PostDto>>(postDto,HttpStatus.OK);
    	
    }
    
    //search by title
    
    @GetMapping("/user/search/{keyword}")
    public ResponseEntity<List<PostDto>> serachPost(@PathVariable String keyword)
    {
    	
      List<PostDto> postDto= this.postService.searchPosts(keyword);
    	
    		 return new ResponseEntity<List<PostDto>>(postDto,HttpStatus.OK);
    }
    
    
     //post image upload
    @PostMapping("user/post/image/upload/{postId}")  
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,@PathVariable int postId ) throws IOException
    {
    
    	String fileName=this.fileService.uploadImage(path, image);
     
     PostDto postById = this.postService.getPostById(postId);
    
      postById.setImageName(fileName);
      
      PostDto updatePost = this.postService.updatePost(postById, postId);
      
      return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    	
    }
    @GetMapping(value="/user/post/images/{postId}",produces=MediaType.IMAGE_PNG_VALUE)
	public void getFile(
			@PathVariable("postId")int postId ,HttpServletResponse response) throws IOException
	{      
    	
    	  PostDto postDto = this.postService.getPostById(postId);
    	    String imageName=postDto.getImageName();
		
           InputStream is=this.fileService.getResource(path, imageName);
		  
           response.setContentType(MediaType.IMAGE_PNG_VALUE);
           
           StreamUtils.copy(is,response.getOutputStream());
		
		
		
	}
    
    
    
    
    
	
}
