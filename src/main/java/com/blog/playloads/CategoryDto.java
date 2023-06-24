package com.blog.playloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	
	private Integer categoryId;
	
	@NotEmpty
	private String categoryTitle;
	@NotEmpty
	private String categoryDescription;
}
