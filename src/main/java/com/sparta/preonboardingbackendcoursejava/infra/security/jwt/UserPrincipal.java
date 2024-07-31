package com.sparta.preonboardingbackendcoursejava.infra.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal {

    private final String memberEmail;
    private final String nickname;
    private final Collection<GrantedAuthority> authorities;

    public UserPrincipal(String memberEmail, String nickname, Collection<GrantedAuthority> authorities) {
        this.memberEmail = memberEmail;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    public UserPrincipal(String memberEmail, String nickname, Set<String> roles) {
        this.memberEmail = memberEmail;
        this.nickname = nickname;
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getNickname() {
        return nickname;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}

