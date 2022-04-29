package com.zahangir.konasl.service.impl;

import com.zahangir.konasl.dto.UserPrincipal;
import com.zahangir.konasl.model.User;
import com.zahangir.konasl.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndIsActiveTrue(username);
        if(user == null){
            throw new UsernameNotFoundException("Username or password invalid");
        }
        return UserPrincipal.create(user);
    }
}
