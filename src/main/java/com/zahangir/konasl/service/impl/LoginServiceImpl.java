package com.zahangir.konasl.service.impl;

import com.zahangir.konasl.model.User;
import com.zahangir.konasl.repository.UserRepository;
import com.zahangir.konasl.service.LoginService;
import com.zahangir.konasl.utils.JwtTokenProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User getLoginUser(String authString, HttpServletRequest request) {
        if(authString == null || authString.equals("")){
            return null;
        }
        String token = getToken(authString);
        if(jwtTokenProvider.isValidToken(token, request)){
            return userRepository.findById(jwtTokenProvider.getUserIdFromToken(token)).orElse(null);
        }
        return null;
    }

    private String getToken(String authString){
        String[] authParts = authString.split("\\s+");
        return authParts[1];
    }
}