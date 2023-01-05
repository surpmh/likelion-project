package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.like.LikeRequest;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.entity.User;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.repository.LikeRepository;
import com.likelion.likelionproject.repository.PostRepository;
import com.likelion.likelionproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    /**
     * 좋아요 개수
     */
    public Long count(Long postsId) {
        return likeRepository.countByPostId(postsId);
    }

    /**
     * 좋아요 누르기
     */
    public String like(Long postsId, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        final String[] message = new String[1];

        likeRepository.findByPostIdAndUserId(postsId, user.getId()).ifPresentOrElse(
                like -> {
                    message[0] = like.likeUpdate(like.isCancel());
                    likeRepository.save(like);
                },
                () -> {
                    message[0] = "좋아요를 눌렀습니다.";
                    likeRepository.save(LikeRequest.toEntity(user, post));
                });

        return message[0];
    }
}
