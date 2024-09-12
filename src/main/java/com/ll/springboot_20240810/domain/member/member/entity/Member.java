package com.ll.springboot_20240810.domain.member.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class Member {
    private Long id;
    private String username;
    private String password;

    public Member(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean isAdmin() {
        return username.equals("admin");
    }

    public List<String> authorities() {
        if (isAdmin()){
            return List.of("ROLE_ADMIN");
        }
        return List.of("ROLE_MEMBER");
    }
}
