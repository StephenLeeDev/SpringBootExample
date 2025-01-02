package com.stephenleedev.SpringBoot.Example.repository;

import com.stephenleedev.SpringBoot.Example.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);

//    @Override
//    Optional<Member> findByNameAndId(String name, Long id);

}
