package com.ll.springboot_20240810.domain.member.service;

import com.ll.springboot_20240810.domain.member.member.entity.Member;
import com.ll.springboot_20240810.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password) {
        password = passwordEncoder.encode(password);
        Member member = new Member(username, password) {
        };
        memberRepository.save(member);

        return member;
    }


    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);

    }

    public void delete(long id) {
        memberRepository.delete(id);
    }

    public void modify(long id, String username, String password) {
        Member member = findById(id).get();
        member.setUsername(username);
        member.setPassword(password);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }


}
