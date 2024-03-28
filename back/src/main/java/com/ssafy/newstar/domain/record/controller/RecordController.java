package com.ssafy.newstar.domain.record.controller;

import com.ssafy.newstar.domain.record.dto.CreateRecordRequest;
import com.ssafy.newstar.domain.record.entity.Record;
import com.ssafy.newstar.domain.record.dto.RecordLikeRequest;
import com.ssafy.newstar.domain.record.service.RecordService;
import com.ssafy.newstar.util.response.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.newstar.domain.record.dto.RecordResponse.createRecordResponse;
import static com.ssafy.newstar.util.response.SuccessResponseEntity.getResponseEntity;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
    @GetMapping("/records")
    public ResponseEntity<?> getRecords(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");


        return getResponseEntity(SuccessCode.OK, createRecordResponse(recordService.getRecords(memberId)));
    }

    @GetMapping("/records/likes")
    public ResponseEntity<?> getRecordLikes(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");

        return getResponseEntity(SuccessCode.OK, createRecordResponse(recordService.getRecordLikes(memberId)));
    }

    @PostMapping("/records")
    public ResponseEntity<?> createRecord(@RequestBody CreateRecordRequest createRecordRequest, HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");

        // 사용자가 이미 시청한 기록이 있는지 확인
        if(recordService.confirmRecord(memberId, createRecordRequest)) {
            Record record = recordService.createRecordEntity(memberId, createRecordRequest);
            recordService.createRecord(record);
        }
        return getResponseEntity(SuccessCode.CREATED);
    }

    @PatchMapping("/records")
    public ResponseEntity<?> updateRecordLikes(HttpServletRequest request, @RequestBody RecordLikeRequest recordLikeRequest) {
        Long memberId = (Long) request.getAttribute("memberId");
        recordService.updateRecordLikes(memberId, recordLikeRequest);
        return getResponseEntity(SuccessCode.OK);
    }
}