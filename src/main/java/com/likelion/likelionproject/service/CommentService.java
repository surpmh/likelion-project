package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.alarm.AlarmRequest;
import com.likelion.likelionproject.dto.comment.CommentDetailResponse;
import com.likelion.likelionproject.dto.comment.CommentRequest;
import com.likelion.likelionproject.entity.Comment;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.entity.User;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.repository.CommentRepository;
import com.likelion.likelionproject.repository.PostRepository;
import com.likelion.likelionproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AlarmService alarmService;

    /**
     * 권한 확인
     */
    private Comment checkPermission(Long id, String userName) {
        userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        return commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));
    }

    /**
     * 댓글 조회
     */
    public Page<CommentDetailResponse> list(Long postsId, Pageable pageable) {
        Page<CommentDetailResponse> commentDetailResponse = commentRepository.findByPostId(postsId, pageable).map(CommentDetailResponse::fromEntity);

        return commentDetailResponse;
    }

    /**
     * 댓글 등록
     */
    public CommentDetailResponse create(Long postsId, CommentRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        Comment savedComment = commentRepository.save(request.toEntity(user, post));

        alarmService.notify(postsId, userName, new AlarmRequest("NEW_COMMENT_ON_POST", "new comment!"));

        return CommentDetailResponse.fromEntity(savedComment);
    }

    /**
     * 댓글 수정
     */
    public CommentDetailResponse edit(Long postsId, Long id, CommentRequest request, String userName) {
        Comment comment = checkPermission(id, userName);
        postRepository.findById(postsId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        comment.commentEdit(request);
        Comment editComment = commentRepository.save(comment);

        return CommentDetailResponse.fromEntity(editComment);
    }

    /**
     * 댓글 삭제
     */
    public void delete(Long id, String userName) {
        Comment comment = checkPermission(id, userName);

        commentRepository.delete(comment);
    }
}
