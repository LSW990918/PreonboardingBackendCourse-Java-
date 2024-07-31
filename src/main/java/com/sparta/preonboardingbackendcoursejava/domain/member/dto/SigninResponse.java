package com.sparta.preonboardingbackendcoursejava.domain.member.dto;

public class SigninResponse {
    private String nickname;
    private String accessToken;

    public SigninResponse() {}

    public SigninResponse(String nickname, String accessToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}