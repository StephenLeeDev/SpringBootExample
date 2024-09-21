package com.stephenleedev.SpringBoot.Example.service;

import com.stephenleedev.SpringBoot.Example.domain.Member;
import com.stephenleedev.SpringBoot.Example.repository.MemberRepository;
import com.stephenleedev.SpringBoot.Example.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);

        repository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        repository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return repository.findAll();
    }

    /**
     * 회원조회
     */
    public Optional<Member> findOne(Long memberId) {
        return repository.findById(memberId);
    }

}
