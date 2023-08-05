package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.Member;
import com.tickettimer.backendserver.domain.Memo;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    // 저장
    public Memo save(Memo memo) {
        return  memoRepository.save(memo);
    }

    // 조회 findById
    public Memo findById(Long id) {
        Optional<Memo> findMemo = memoRepository.findById(id);
        return findMemo.orElseThrow(
                () -> new CustomNotFoundException("memoId", id.toString())
        );
    }

    // 조회 findByMember
    public List<Memo> findByMember(Member member) {
        return memoRepository.findByMember(member);
    }

    // 수정
    public Memo update(Long id, Memo newMemo) {
        Memo memo = memoRepository.findById(id).get();
        memo = newMemo;
        return memoRepository.save(memo);
    }

    // 삭제
    public void delete(Long id) {
        memoRepository.deleteById(id);
    }
}
