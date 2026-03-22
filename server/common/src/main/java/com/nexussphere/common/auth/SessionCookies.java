package com.nexussphere.common.auth;


import lombok.Getter;

@Getter
public enum SessionCookies {


    COOKIE_SESSION("SESSION", "/api/v1/auth/generate-authorization"),
    COOKIE_SETUP_PROFILE("SESSION_SETUP_PROFILE","/api/v1/auth/setup-profile");

    private final String path;
    private final String cookieName;


    SessionCookies(String name, String path) {
        this.cookieName = name;
        this.path = path;
    }
}
