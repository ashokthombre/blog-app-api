package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entitis.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
