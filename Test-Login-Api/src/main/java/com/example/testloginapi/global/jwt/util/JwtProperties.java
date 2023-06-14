package com.example.testloginapi.global.jwt.util;

public class JwtProperties {

    public static final Long ACCESS_TOKEN_EXPIRED = 1000 * 60 * 30L; // 30분
    public static final Long REFRESH_TOKEN_EXPIRED = 1000* 60 * 60 * 12L; // 12시간
}
