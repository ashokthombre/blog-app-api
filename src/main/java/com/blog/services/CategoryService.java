package com.blog.services;

import java.util.List;

import com.blog.playloads.CategoryDto;


public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	public CategoryDto getCateoryById(Integer categoryId);
	 
	 public List<CategoryDto> getAllCategory();
	 
	  public void deleteCategory(Integer categoryId);
	

}
