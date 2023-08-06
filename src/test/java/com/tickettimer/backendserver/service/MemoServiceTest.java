package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Memo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemoServiceTest {

    @Autowired
    MemoService memoService;
    @Autowired
    MemberService memberService;

    @Test
    void save() {
        // given
        Member member1 = Member.builder()
                .serverId("aa1")
                .nickname("aa2")
                .email("aa3")
                .profileUrl("aa4")
                .password("aa5")
                .build();
        memberService.save(member1);

        Memo memo1 = Memo.builder()
                .member(member1)
                .content("content22")
                .createdAt(LocalDate.now())
                .build();

        // when
        memoService.save(memo1);

        // then
        Memo findMemo = memoService.findById(1L);
        Assertions.assertThat(findMemo.getContent()).isEqualTo("content22");
    }

    @Test
    void findByMember() {
        Member member1 = Member.builder()
                .serverId("aa1")
                .nickname("aa2")
                .email("aa3")
                .profileUrl("aa4")
                .password("aa5")
                .build();
        memberService.save(member1);

        Memo memo1 = Memo.builder()
                .member(member1)
                .content("content11")
                .createdAt(LocalDate.now())
                .build();

        Memo memo2 = Memo.builder()
                .member(member1)
                .content("content22")
                .createdAt(LocalDate.now())
                .build();

        // when
        memoService.save(memo1);
        memoService.save(memo2);

        // then
        List<Memo> list = memoService.findByMember(member1);
        for (Memo memo : list) {
            System.out.println(memo.getContent());
        }
    }

    @Test
    void uodate() {
        // given
        Member member1 = Member.builder()
                .serverId("aa1")
                .nickname("aa2")
                .email("aa3")
                .profileUrl("aa4")
                .password("aa5")
                .build();
        memberService.save(member1);

        Memo memo1 = Memo.builder()
                .member(member1)
                .content("content22")
                .createdAt(LocalDate.now())
                .build();
        memoService.save(memo1);

        // when
        memo1.setContent("update");
        memoService.update(1L, memo1);

        // then
        Memo findMemo = memoService.findById(1L);
        Assertions.assertThat(findMemo.getContent()).isEqualTo("update");
    }

    @Test
    void delete() {
        // given
        Member member1 = Member.builder()
                .serverId("aa1")
                .nickname("aa2")
                .email("aa3")
                .profileUrl("aa4")
                .password("aa5")
                .build();
        memberService.save(member1);

        Memo memo1 = Memo.builder()
                .member(member1)
                .content("content22")
                .createdAt(LocalDate.now())
                .build();
        memoService.save(memo1);

        memoService.delete(1L);

        // then
    }

}