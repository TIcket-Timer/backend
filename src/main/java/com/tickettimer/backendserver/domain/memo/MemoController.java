package com.tickettimer.backendserver.domain.memo;

import com.tickettimer.backendserver.domain.member.Member;
import com.tickettimer.backendserver.global.dto.ResultResponse;
import com.tickettimer.backendserver.domain.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memos")
public class MemoController {
    private final MemoService memoService;
    private final MemberService memberService;

//     메모 저장
    @PostMapping
    public ResponseEntity<ResultResponse> postMemo(HttpServletRequest request, @RequestBody MemoRequestDto memoRequestDto) {
        Long memberId = (Long) request.getAttribute("id");
        Member member = memberService.findById(memberId);

        Memo memo = Memo.builder()
                .content(memoRequestDto.getContent())
                .member(member)
                .date(memoRequestDto.getDate())
                .build();

        // Memo 엔티티 저장
        MemoResponseDto responseDto = new MemoResponseDto(memoService.save(memo));

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("메모 저장")
                .result(responseDto).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    // 사용자가 작성한 메모 중 특정 날짜에 작성한 메모만 조회
//    @GetMapping("/{localDate}")
//    public ResponseEntity<ResultResponse> getMemoByDate(@PathVariable @JsonDeserialize LocalDate localDate) {
//        // Memo 엔티티 저장
//        MemoResponseDto responseDto = new MemoResponseDto(memoService.findByDate(localDate));
//
//        ResultResponse res = ResultResponse.builder()
//                .code(HttpStatus.CREATED.value())
//                .message("findByDate 메모 조회")
//                .result(responseDto).build();
//        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getMemoById(@PathVariable Long id) {
        // Memo 엔티티 저장
        MemoResponseDto responseDto = new MemoResponseDto(memoService.findById(id));

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("findById 메모 조회")
                .result(responseDto).build();
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

     // 해당 사용자가 작성한 모든 메모 조회
    @GetMapping
    public ResponseEntity<ResultResponse> getAllMemo(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("id");
        Member member = memberService.findById(memberId);

        AllMemoResponseDto responseDto = new AllMemoResponseDto(memoService.findByMember(member));

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("findByMember 메모 조회")
                .result(responseDto).build();

        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deleteMemoById(@PathVariable Long id) {
        memoService.delete(id);

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("메모 삭제")
                .build();

        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponse> editMemoById(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto) {
        memoService.update(id, memoRequestDto.getContent());

        ResultResponse res = ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("메모 내용 수정")
                .build();

        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getCode()));
    }
}
