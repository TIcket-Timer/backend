package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MusicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicalService {
    private final MusicalRepository musicalRepository;

    // 저장
    public Musical save(Musical musical) {
        return  musicalRepository.save(musical);
    }

    // 조회 findById
    public Musical findById(String id) {
        Optional<Musical> findMusical = musicalRepository.findById(id);
        return findMusical.orElseThrow(
                () -> new CustomNotFoundException("musicalId", id)
        );
    }

    // id 리스트로 받으면 리스트로 뮤지컬 반환
    // 탑 랭킹 구할 때 사용
    // TopRankingService에서 id 리스트 받아서 여기에 넘겨줌
    public List<Musical> findByIds(List<String> ids) {
        List<Musical> musicals = new ArrayList<>();
        for (String id : ids) {
            Optional<Musical> musical = musicalRepository.findById(id);
            if (musical.isEmpty()) {
                throw new CustomNotFoundException("musicalId", id);
            }
            musicals.add(musical.get());
        }
        return musicals;
    }
    // 최근 등록 조회
    public List<Musical> findLatestNotice(Pageable pageable) {
        Page<Musical> musicals = musicalRepository.findAllByOrderByCreatedTimeDesc(pageable);
        List<Musical> musicalNotices = musicals.getContent();
        return musicalNotices;
    }

    // 수정
    public Musical update(String id, Musical newMusical) {
        Musical musical = musicalRepository.findById(id).get();
        musical = newMusical;
        return musicalRepository.save(musical);
    }
    // 삭제
    public void delete(String id) {
        musicalRepository.deleteById(id);
    }

    // 모든 뮤지컬
    public List<Musical> findAll() {
        List<Musical> res = musicalRepository.findAll();
        return res;
    }
    // 모든 뮤지컬 페이징
    public List<Musical> findAllPagination(Pageable pageable) {
        Page<Musical> res = musicalRepository.findAll(pageable);
        List<Musical> musicals = res.getContent();
        return musicals;
    }
    public List<Musical> findBySiteCategory(SiteCategory siteCategory, Pageable pageable) {
        List<Musical> res = musicalRepository.findBySiteCategory(siteCategory, pageable);
        return res;
    }

    /**
     * 뮤지컬 제목에 name이 포함된 뮤지컬 정보를 가져온다.
     * @param name : 제목
     * @return 뮤지컬 정보 리스트
     */
    public List<Musical> findByNameAndSite(String name, SiteCategory siteCategory, Pageable pageable) {
        List<Musical> res = musicalRepository.findByNameAndSiteCategory(name, siteCategory.name(), pageable);
        return res;
    }

}
