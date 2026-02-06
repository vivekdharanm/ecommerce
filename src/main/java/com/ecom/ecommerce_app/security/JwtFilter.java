package com.ecom.ecommerce_app.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecom.ecommerce_app.entity.User;
import com.ecom.ecommerce_app.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter 
{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException 
    {

        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        //Check Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) 
        {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        //Authenticate user if not already authenticated
        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) 
        {

            User user = userRepository.findByUsername(username);

            if (user != null && jwtUtil.validateToken(token, username)) 
            {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                null,
                                List.of(new SimpleGrantedAuthority(
                                        "ROLE_" + user.getRole().name()
                                ))
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //Continue filter chain
        filterChain.doFilter(request, response);
    }
}
