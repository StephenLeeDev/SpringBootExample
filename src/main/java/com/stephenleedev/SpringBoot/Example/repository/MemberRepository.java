package com.stephenleedev.SpringBoot.Example.repository;

import com.stephenleedev.SpringBoot.Example.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    void clearStore();

}
