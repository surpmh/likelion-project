package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.comment.CommentDto;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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
     * 댓글 등록
     */
    public CommentDto create(Long postsId, CommentRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        Comment savedComment = commentRepository.save(request.toEntity(user, post));

        return CommentDto.builder()
                .id(savedComment.getId())
                .comment(savedComment.getComment())
                .userName(savedComment.getUser().getUserName())
                .postId(savedComment.getPost().getId())
                .createdAt(savedComment.getCreatedAt())
                .lastModifiedAt(savedComment.getLastModifiedAt())
                .build();
    }

    /**
     * 댓글 수정
     */
    public CommentDto edit(Long postsId, Long id, CommentRequest request, String userName) {
        Comment comment = checkPermission(id, userName);
        postRepository.findById(postsId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        comment.commentEdit(request);
        Comment editComment = commentRepository.save(comment);

        return CommentDto.builder()
                .id(editComment.getId())
                .comment(editComment.getComment())
                .userName(editComment.getUser().getUserName())
                .postId(editComment.getPost().getId())
                .createdAt(editComment.getCreatedAt())
                .lastModifiedAt(editComment.getLastModifiedAt())
                .build();
    }

    /**
     * 댓글 삭제
     */
    public void delete(Long id, String userName) {
        Comment comment = checkPermission(id, userName);

        commentRepository.delete(comment);
    }
}
