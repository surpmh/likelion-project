package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.alarm.AlarmDetailResponse;
import com.likelion.likelionproject.dto.alarm.AlarmRequest;
import com.likelion.likelionproject.dto.alarm.AlarmResponse;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.entity.User;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.repository.AlarmRepository;
import com.likelion.likelionproject.repository.PostRepository;
import com.likelion.likelionproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmRepository alarmRepository;

    /**
     * 알람 리스트
     */
    public AlarmResponse list(String userName, Pageable pageable) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Page<AlarmDetailResponse> alarmDetailResponses = alarmRepository.findByUserId(user.getId(), pageable).map(AlarmDetailResponse::fromEntity);

        AlarmResponse alarmResponse = AlarmResponse.builder()
                .content(alarmDetailResponses.getContent())
                .build();

        return alarmResponse;
    }

    /**
     * 알람 등록
     */
    public void notify(Long PostsId, String fromUserName, AlarmRequest request) {
        User fromUser = userRepository.findByUserName(fromUserName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.findById(PostsId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        User targetUser = userRepository.findByUserName(post.getUser().getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        if (fromUser != targetUser) {
            alarmRepository.save(request.toEntity(post, fromUser, targetUser));
        }
    }
}