package com.hw.hellospring;

import com.hw.hellospring.repository.JdbcMemberRepository;
import com.hw.hellospring.repository.JdbcTemplateMemberRepository;
import com.hw.hellospring.repository.MemberRepository;
import com.hw.hellospring.repository.MemoryMemberRepository;
import com.hw.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
// 자동 의존관계 설정을 하지 않으면 이렇게 수동으로 등록 해야 함