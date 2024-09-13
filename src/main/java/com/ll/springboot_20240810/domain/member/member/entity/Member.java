package com.ll.springboot_20240810.domain.member.member.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Member {
    @EqualsAndHashCode.Include
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

    public List<String> getAuthorities() {
        if (isAdmin()){
            return List.of("ROLE_ADMIN");
        }
        return List.of("ROLE_MEMBER");
    }
}
