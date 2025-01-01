package com.stephenleedev.SpringBoot.Example.service;

import com.stephenleedev.SpringBoot.Example.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
//@Commit
class MemberServiceIntegrationTest {

    @Autowired MemberService service;

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long memberId = service.join(member);

        // then
        Member found = service.findOne(memberId).get();
        assertThat(member.getName()).isEqualTo(found.getName());
    }

    @Test
    void validateDuplicateMember() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        service.join(member1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }

}