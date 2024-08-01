package com.sparta.preonboardingbackendcoursejava.domain.member.service;

import com.sparta.preonboardingbackendcoursejava.domain.common.error.ErrorCode;
import com.sparta.preonboardingbackendcoursejava.domain.common.error.exception.MemberDuplicateException;
import com.sparta.preonboardingbackendcoursejava.domain.common.error.exception.PasswordMismatchException;
import com.sparta.preonboardingbackendcoursejava.domain.member.dto.MemberResponse;
import com.sparta.preonboardingbackendcoursejava.domain.member.dto.MemberSigninRequest;
import com.sparta.preonboardingbackendcoursejava.domain.member.dto.MemberSignupRequest;
import com.sparta.preonboardingbackendcoursejava.domain.member.dto.SigninResponse;
import com.sparta.preonboardingbackendcoursejava.domain.member.model.MemberEntity;
import com.sparta.preonboardingbackendcoursejava.domain.member.model.MemberRole;
import com.sparta.preonboardingbackendcoursejava.domain.member.repository.MemberRepository;
import com.sparta.preonboardingbackendcoursejava.infra.security.jwt.JwtPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberAuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtPlugin jwtPlugin;

    @Autowired
    public MemberAuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtPlugin jwtPlugin) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtPlugin = jwtPlugin;
    }

    @Transactional
    public MemberResponse signup(MemberSignupRequest memberSignupRequest, MemberRole memberRole) {
        if (memberRepository.existsByEmail(memberSignupRequest.getEmail())) {
            throw new MemberDuplicateException(ErrorCode.MEMBER_EMAIL_DUPLICATE);
        }
        if (memberRepository.existsByNickname(memberSignupRequest.getNickname())) {
            throw new MemberDuplicateException(ErrorCode.MEMBER_NICKNAME_DUPLICATE);
        }
        if (!memberSignupRequest.getPassword().equals(memberSignupRequest.getPasswordConfirm())) {
            throw new PasswordMismatchException(ErrorCode.MEMBER_PASSWORD_MISMATCH);
        }

        MemberEntity member = memberRepository.save(new MemberEntity(
                memberSignupRequest.getEmail(),
                passwordEncoder.encode(memberSignupRequest.getPassword()),
                memberSignupRequest.getNickname(),
                memberRole
        ));
        return member.toResponse();
    }

    @Transactional
    public SigninResponse signin(MemberSigninRequest signinRequest) {
        MemberEntity member = memberRepository.findByEmail(signinRequest.getEmail());
        if (!passwordEncoder.matches(signinRequest.getPassword(), member.getPassword())) {
            throw new PasswordMismatchException(ErrorCode.MEMBER_PASSWORD_MISMATCH);
        }

        String accessToken = jwtPlugin.generateAccessToken(member.getEmail(), member.getNickname());
        return new SigninResponse(member.getNickname(), accessToken);
    }
}

