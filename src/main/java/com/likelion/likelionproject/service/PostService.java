package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.PostWriteRequest;
import com.likelion.likelionproject.dto.PostDto;
import com.likelion.likelionproject.entity.Post;
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
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "Post Not Found."));
    }

    // 포스트 등록
    public PostDto create(PostWriteRequest request, String userName) {
        userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, "User Not Found."));

        Post savedPost = postRepository.save(request.toEntity());

        return PostDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .build();
    }

    // 포스트 수정
    public PostDto edit(Long id, PostWriteRequest request, String userName) {
        userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, "User Not Found."));
        postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "Post Not Found."));
        Post post = postRepository.findById(id)
                .filter(posts -> Objects.equals(posts.getUser().getUserName(), userName))
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PERMISSION, "Invalid Permission."));
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
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, "User Not Found."));
        postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "Post Not Found."));
        postRepository.findById(id)
                .filter(posts -> Objects.equals(posts.getUser().getUserName(), userName))
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PERMISSION, "Invalid Permission."));
        postRepository.deleteById(id);
    }
}
