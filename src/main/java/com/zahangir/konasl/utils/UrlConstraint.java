package com.zahangir.konasl.utils;

public final class UrlConstraint {
    private UrlConstraint() {
    }

    private static final String VERSION = "/v1";
    private static final String API = "/api";

    public static class AuthManagement {
        public static final String ROOT = API + "/auth";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String REGISTER = "/register";
    }

    public static class DashboardManagement {
        public static final String ROOT = API + VERSION + "/dashboard";
        public static final String GET_ALL = "/all";
    }

}
