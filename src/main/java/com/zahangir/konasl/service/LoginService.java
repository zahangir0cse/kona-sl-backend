package com.zahangir.konasl.service;

import com.zahangir.konasl.model.User;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    User getLoginUser(String authString, HttpServletRequest request);
}
