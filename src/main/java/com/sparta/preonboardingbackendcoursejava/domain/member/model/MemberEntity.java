package com.sparta.preonboardingbackendcoursejava.domain.member.model;


import com.sparta.preonboardingbackendcoursejava.domain.member.dto.MemberResponse;
import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "role")
    private MemberRole role;

    public MemberEntity() {
    }

    public MemberEntity(String email, String password, String nickname, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = memberRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public MemberRole getRole() { return role; }

    public  void setRole(MemberRole role) { this.role = role; }

    public MemberResponse toResponse() {
        return new MemberResponse(email, nickname, role
        );
    }
}

