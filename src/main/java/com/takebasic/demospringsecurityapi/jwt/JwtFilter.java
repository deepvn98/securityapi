package com.takebasic.demospringsecurityapi.jwt;


import com.takebasic.demospringsecurityapi.service.iplm.UserServiceIplm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserServiceIplm userServiceIplm;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           String jwt = getJwt(request);
           if (jwt != null && jwtTokenProvider.validateToken(jwt)){
               String username = jwtTokenProvider.getUerNameFromToken(jwt);
               UserDetails userDetails = userServiceIplm.loadUserByUsername(username);
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
           }
       }catch (Exception e){
           logger.error("Can't set user authentication -> Message: {}", e);
       }
        filterChain.doFilter(request, response);
    }

    // lấy ra token trong reqest.
    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return authHeader.replace("Bearer", "");
        }
        return null;
    }
}
