package com.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtAutenticatinFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get token
//		HttpSession session = request.getSession();
//		String token1=(String) session.getAttribute("token");
//		 
//		System.err.println(token1);
//		String requestToken="Bearer "+token1;
//		System.err.println(requestToken);
		String requestToken=request.getHeader("Authorization");
		System.out.println(requestToken);
		String username=null;
		
		String token=null;
		
		
		
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			
			token=requestToken.substring(7);
			
			try 
			{
				username=this.jwtTokenHelper.getUsernameFromToken(token);	
				 System.err.println(username);
			} 
			catch (IllegalArgumentException e)
			{
				System.err.println("Unable to get jwt token");
			}
			catch(ExpiredJwtException e)
			{
				System.err.println("Jwt token has expired..");
				
			}
			catch (MalformedJwtException e)
			{
				System.err.println("invalid jwt");
			}
		 
			
		}
		else
		{
			System.out.println("JWT token does not begin whth Bearer");
 		}
		
		// once we get the token ,now validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			
			{
				System.err.println("Invalid jwt token");
			}
			
		}
		else
		{
			System.err.println("username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
		
		
		
		
		
	}

}
