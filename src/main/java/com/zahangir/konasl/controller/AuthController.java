package com.zahangir.konasl.controller;

import com.zahangir.konasl.annotations.ApiController;
import com.zahangir.konasl.dto.LoginDto;
import com.zahangir.konasl.dto.Response;
import com.zahangir.konasl.dto.UserRegisterDto;
import com.zahangir.konasl.service.AuthService;
import com.zahangir.konasl.utils.UrlConstraint;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.AuthManagement.ROOT)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(UrlConstraint.AuthManagement.LOGIN)
    public Response login(@RequestBody @Valid LoginDto loginDto, BindingResult result, HttpServletRequest request, HttpServletResponse response){
        return authService.login(loginDto, request);
    }

    @PostMapping(UrlConstraint.AuthManagement.LOGOUT)
    public Response logout(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        return authService.logout(session);
    }

    @PostMapping(UrlConstraint.AuthManagement.REGISTER)
    private Response register(@RequestBody UserRegisterDto userRegisterDto, HttpServletResponse response, HttpServletRequest request) {
        return authService.register(userRegisterDto);
    }

}
