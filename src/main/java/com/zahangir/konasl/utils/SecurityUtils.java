package com.zahangir.konasl.utils;

import com.zahangir.konasl.dto.UserPrincipal;
import com.zahangir.konasl.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    private static UserPrincipal getPrincipal() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                return (UserPrincipal) authentication.getPrincipal();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static User getCurrentUser() {
        UserPrincipal principal = getPrincipal();
        if(principal != null){
            return principal.getUser();
        }
        return null;
    }


    public static Long getLoggedInUserId() {
        UserPrincipal principal = getPrincipal();
        if(principal != null){
            return principal.getId();
        }
        return null;
    }

    public static String getLoggedInUsername(){
        UserPrincipal principal = getPrincipal();
        if(principal != null){
            return principal.getUsername();
        }
        return null;
    }

    public static Collection<? extends GrantedAuthority> getLoggedInAuthorities(){
        UserPrincipal principal = getPrincipal();
        if(principal != null){
            return principal.getAuthorities();
        }
        return null;
    }
}