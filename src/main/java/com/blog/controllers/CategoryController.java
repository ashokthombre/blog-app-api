package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.playloads.ApiResponse;
import com.blog.playloads.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	//create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		 
        CategoryDto categoryDto2=this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(categoryDto2,HttpStatus.CREATED);
		
		
		
	}
	
	
	//update category
	
	@PutMapping("/cupdate/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("categoryId") int categoryId )
	{
		
	CategoryDto categoryDto2=this.categoryService.updateCategory(categoryDto, categoryId);
		
	return new ResponseEntity<CategoryDto>(categoryDto2,HttpStatus.OK);
		
	}
	
	
	//delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int categoryId)
	{
      		
		this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully.!",true),HttpStatus.OK);
	}
	
	//get category by id
    
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>getCategoryById(@PathVariable("categoryId") int categoryId)
	{
	CategoryDto categoryDto=this.categoryService.getCateoryById(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
		
	}
	
	
	
	
	//get all category
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}
	
	

}
