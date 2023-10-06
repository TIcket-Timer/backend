package com.tickettimer.backendserver.service;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import com.tickettimer.backendserver.exception.CustomNotFoundException;
import com.tickettimer.backendserver.repository.MusicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
