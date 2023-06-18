package com.example.testloginapi.domain.refreshToken.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken") // 만료기한 : 3일
public class RefreshToken {

    @Id
    private String authId;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long expired;
}
