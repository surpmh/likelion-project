package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.Response;
import com.likelion.likelionproject.dto.alarm.AlarmDetailResponse;
import com.likelion.likelionproject.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    /**
     * 알람 리스트
     */
    @GetMapping("")
    public Response<Page<AlarmDetailResponse>> alarm(@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        Page<AlarmDetailResponse> alarmDetailResponses = alarmService.list(authentication.getName(), pageable);
        return Response.success(alarmDetailResponses);
    }
}