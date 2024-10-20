package com.stephenleedev.SpringBoot.Example;

import com.stephenleedev.SpringBoot.Example.repository.JdbcMemberRepository;
import com.stephenleedev.SpringBoot.Example.repository.JdbcTemplateMemberRepository;
import com.stephenleedev.SpringBoot.Example.repository.MemberRepository;
import com.stephenleedev.SpringBoot.Example.repository.MemoryMemberRepository;
import com.stephenleedev.SpringBoot.Example.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

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
