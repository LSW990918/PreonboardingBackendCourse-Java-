package com.sparta.preonboardingbackendcoursejava.domain.member.repository;

import com.sparta.preonboardingbackendcoursejava.domain.member.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    MemberEntity findByEmail(String email);
}

