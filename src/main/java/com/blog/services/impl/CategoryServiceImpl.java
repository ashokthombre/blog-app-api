package com.blog.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.blog.entitis.Category;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.playloads.CategoryDto;

import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		 
		Category category= this.dtoToCategory(categoryDto);
		 
		Category savedCategory=this.categoryRepo.save(category);
		
		return this.categoryToDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		
		Category categoryUpdate=this.categoryRepo.save(category);
		
		
		
		return this.categoryToDto(categoryUpdate);
		
		
	
	}

	@Override
	public CategoryDto getCateoryById(Integer categoryId) {
		   
	Category category=this.categoryRepo.findById(categoryId)
			.orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
		
		
		
		return this.categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		 List<Category> categorys= this.categoryRepo.findAll();

		 List<CategoryDto> categoryDtos=categorys.stream().map(category-> this.categoryToDto(category)).collect(Collectors.toList()); 
		 
		return categoryDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
	    
		this.categoryRepo.delete(category);
	}
	
	private Category dtoToCategory(CategoryDto categoryDto)
	{
		Category category=this.modelMapper.map(categoryDto,Category.class);
		
		
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return category;
		
	}
	
	private CategoryDto categoryToDto(Category category)
	{
		
	 CategoryDto categoryDto=this.modelMapper.map(category, CategoryDto.class);
		
	  return categoryDto;
	}
	
	
}
