package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.post.PostWriteRequest;
import com.likelion.likelionproject.dto.post.PostDto;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.entity.User;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.repository.PostRepository;
import com.likelion.likelionproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 포스트 상세
    public Post detail(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
    }

    // 포스트 등록
    public PostDto create(PostWriteRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post savedPost = postRepository.save(request.toEntity(user));

        return PostDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .build();
    }

    // 포스트 수정
    public PostDto edit(Long id, PostWriteRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(id)
                .filter(posts -> Objects.equals(posts.getUser().getId(), user.getId()))
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));
        post.postEdit(request);
        Post editPost = postRepository.save(post);

        return PostDto.builder()
                .id(editPost.getId())
                .title(editPost.getTitle())
                .body(editPost.getBody())
                .build();
    }

    // 포스트 삭제
    public void delete(Long id, String userName) {
        userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        postRepository.findById(id)
                .filter(posts -> Objects.equals(posts.getUser().getUserName(), userName))
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));
        postRepository.deleteById(id);
    }
}
