package com.sparta.preonboardingbackendcoursejava.domain.member.controller;

import com.sparta.preonboardingbackendcoursejava.domain.member.dto.MemberResponse;
import com.sparta.preonboardingbackendcoursejava.domain.member.dto.MemberSigninRequest;
import com.sparta.preonboardingbackendcoursejava.domain.member.dto.MemberSignupRequest;
import com.sparta.preonboardingbackendcoursejava.domain.member.dto.SigninResponse;
import com.sparta.preonboardingbackendcoursejava.domain.member.model.MemberRole;
import com.sparta.preonboardingbackendcoursejava.domain.member.service.MemberAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class MemberAuthController {

    private final MemberAuthService authService;

    public MemberAuthController(MemberAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(
            @RequestParam MemberRole memberRole,
            @RequestBody MemberSignupRequest memberSignupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(memberSignupRequest, memberRole));
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody MemberSigninRequest signinRequest) {
        SigninResponse test = authService.signin(signinRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, generateCookie(test.getAccessToken()).toString())
                .body(test);
    }

    private ResponseCookie generateCookie(String accessToken) {
        return ResponseCookie.from("SPARTA_ACCESS_TOKEN", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
    }
}
