package com.zahangir.konasl.service.impl;

import com.zahangir.konasl.dto.*;
import com.zahangir.konasl.model.Role;
import com.zahangir.konasl.model.User;
import com.zahangir.konasl.repository.RoleRepository;
import com.zahangir.konasl.repository.UserRepository;
import com.zahangir.konasl.service.AuthService;
import com.zahangir.konasl.utils.JwtTokenProvider;
import com.zahangir.konasl.utils.ResponseBuilder;
import com.zahangir.konasl.utils.RoleConstraint;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Service("authService")
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, ModelMapper modelMapper, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Response login(LoginDto loginDto, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if(authentication.isAuthenticated()){
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            String token  = jwtTokenProvider.generateToken(authentication, request);
            LoginResponseDto dto = new LoginResponseDto();
            dto.setToken(token);
            dto.setId(principal.getId());
            dto.setUsername(principal.getUsername());
            dto.setAuthorities(principal.getAuthorities());
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, dto, "Login Successful");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.UNAUTHORIZED, "Invalid Username or password");
    }

    @Override
    public Response register(UserRegisterDto userRegisterDto) {
        int countExistUsername = userRepository.countByUsername(userRegisterDto.getUsername());
        if(countExistUsername > 0){
            return ResponseBuilder.getFailResponse(HttpStatus.NOT_ACCEPTABLE, "Username already exist.");
        }
        User user = modelMapper.map(userRegisterDto, User.class);
        user.setUsername(user.getUsername());
        String roleUser = RoleConstraint.USER_ROLE;
        Role role = roleRepository.findByRole(roleUser);
        Set<Role> roleSet = new HashSet<>();
        if(role == null){
            role = new Role();
            role.setRole(roleUser);
            role.setName("User");
        }
        roleSet.add(role);
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        if(user != null){
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, null, "User Registered");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @Override
    public Response logout(HttpSession session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.invalidate();
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, null, "Logout successful.");
    }
}
