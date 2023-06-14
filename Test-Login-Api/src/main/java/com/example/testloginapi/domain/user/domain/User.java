package com.example.testloginapi.domain.user.domain;

import com.example.testloginapi.domain.auth.controller.dto.GoogleResponseUserInfoDto;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String providerId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String providerId, String email, String name, String profileUrl, Role role, Provider provider) {
        this.providerId = providerId;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
        this.provider = provider;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public User updateUser(GoogleResponseUserInfoDto userInfo) {
        this.name = userInfo.getName();
        this.profileUrl = userInfo.getPicture();
        return this;
    }
}
