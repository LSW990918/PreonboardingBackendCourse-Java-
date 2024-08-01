package com.sparta.preonboardingbackendcoursejava.domain.member.dto;

import com.sparta.preonboardingbackendcoursejava.domain.member.model.MemberRole;

public class MemberResponse {
    private String email;
    private String nickname;
    private MemberRole memberRole;

    public MemberResponse(String email, String nickname, MemberRole memberRole) {
        this.email = email;
        this.nickname = nickname;
        this.memberRole = memberRole;
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

    public MemberRole getMemberRole() { return memberRole; }

    public void setMemberRole(MemberRole memberRole) { this.memberRole = memberRole; }
}
