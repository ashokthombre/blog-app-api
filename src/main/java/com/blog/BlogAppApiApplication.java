package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstant;
import com.blog.entitis.Role;
import com.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {
  
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		 Role role=new Role();
		 role.setId(AppConstant.ADMIN_USER);
		 role.setName("ROLE_ADMIN");
		 
		 Role role1=new Role();
		 role1.setId(AppConstant.NORMAL_USER);
		 role1.setName("ROLE_NORMAL");
		 
		  List<Role> roles = List.of(role,role1);
		
		   List<Role> result = this.roleRepo.saveAll(roles);
		   
		   for (Role r : result) {
			
			    System.out.println(r.getName());
		}
	}

	

}
