package com.example.testloginapi.domain.auth.controller.dto;

import lombok.Getter;

@Getter
public class GoogleResponseUserInfoDto {

    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String given_name;
    private String picture;
    private String locale;
}
