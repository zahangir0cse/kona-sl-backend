package com.zahangir.konasl.config;

import com.zahangir.konasl.model.User;
import com.zahangir.konasl.service.LoginService;
import com.zahangir.konasl.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final LoginService loginService;
    private final UserDetailsServiceImpl customUserDetailsService;
    public JwtAuthFilter(LoginService loginService, UserDetailsServiceImpl customUserDetailsService){
        this.loginService = loginService;
        this.customUserDetailsService = customUserDetailsService;
    }

    private User getAuthenticatedUser(HttpServletRequest request){
        String authString = null;
        Enumeration<String> headerNameEnumeration = request.getHeaderNames();
        while (headerNameEnumeration.hasMoreElements()){
            String headerKey = headerNameEnumeration.nextElement();
            if(headerKey.equalsIgnoreCase("Authorization")){
                authString = request.getHeader(headerKey);
                break;
            }
        }
        return loginService.getLoginUser(authString, request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        User user = getAuthenticatedUser(httpServletRequest);
        if(user != null){
            try {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
