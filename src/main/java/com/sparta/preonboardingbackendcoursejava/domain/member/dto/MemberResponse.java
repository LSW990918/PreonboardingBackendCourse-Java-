package com.sparta.preonboardingbackendcoursejava.domain.member.dto;

public class MemberResponse {
    private String email;
    private String nickname;

    public MemberResponse(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
