package com.zahangir.konasl.service;

import com.zahangir.konasl.dto.LoginDto;
import com.zahangir.konasl.dto.Response;
import com.zahangir.konasl.dto.UserRegisterDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AuthService {
    Response login(LoginDto loginDto, HttpServletRequest request);

    Response register(UserRegisterDto userRegisterDto);

    Response logout(HttpSession session);
}
