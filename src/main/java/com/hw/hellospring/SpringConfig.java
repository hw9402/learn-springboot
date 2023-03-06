package com.hw.hellospring;

import com.hw.hellospring.repository.MemberRepository;
import com.hw.hellospring.repository.MemoryMemberRepository;
import com.hw.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
// 자동 의존관계 설정을 하지 않으면 이렇게 수동으로 등록 해야 함