package com.blog.config;

import java.security.AuthProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.blog.security.CustomUserDetailService;
import com.blog.security.JwtAutenticatinFilter;
import com.blog.security.JwtAuthenticationEntryPoint;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableWebMvc

public class SecurityConfig  {
    
	public static final String[] PUBLIC_URLS= {
	        "/api/v1/auth/**", 	
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
			
	};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
    
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	 
	@Autowired
	private JwtAutenticatinFilter jwtAutenticatinFilter;
	
	@Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	     
	        http.
	              csrf()
  	              .disable()
	              .authorizeHttpRequests()
	              .requestMatchers(PUBLIC_URLS).permitAll()	             
	             
	           
	     
	              .requestMatchers(HttpMethod.GET).permitAll()
	              .anyRequest()
	              .authenticated()
	              .and()
	              
	              .exceptionHandling()
	              .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
	             .and()
	             .sessionManagement()
	             .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        
	        
	        
	        
	         http.addFilterBefore(this.jwtAutenticatinFilter,UsernamePasswordAuthenticationFilter.class);
	     
	         http.authenticationProvider(daoAuthenticationProvider());
	           DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
	         return defaultSecurityFilterChain;
	    }
	  
	  
//	  
//	  @Bean
//	  public DaoAuthenticationConfigurer<AuthenticationManagerBuilder, CustomUserDetailService> auth(AuthenticationManagerBuilder auth) throws Exception {
//          
//		  
//		 DaoAuthenticationConfigurer<AuthenticationManagerBuilder, CustomUserDetailService> authenticationConfigurer = auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
//	    
//		 return authenticationConfigurer;
//	  }
//	  
	  @Bean
	  public PasswordEncoder passwordEncoder()
	  {
		return new BCryptPasswordEncoder();
	  }
	  
	  @Bean
	  public AuthenticationManager authenticationManager(
	          AuthenticationConfiguration authConfig) throws Exception {
	      return authConfig.getAuthenticationManager();
	  }
	  
	  @Bean
	  public DaoAuthenticationProvider daoAuthenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(this.customUserDetailService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	  }
	  
	 
	 
	 
}
