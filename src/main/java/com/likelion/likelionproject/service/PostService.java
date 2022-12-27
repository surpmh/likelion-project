package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.PostWriteRequest;
import com.likelion.likelionproject.dto.PostDto;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post detail(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "Post not founded."));
    }

    public PostDto create(PostWriteRequest request) {
        Post savedPost = postRepository.save(request.toEntity());

        return PostDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .build();
    }

    public PostDto edit(Long id, PostWriteRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "Post not founded."));
        post.postEdit(request);
        Post editPost = postRepository.save(post);

        return PostDto.builder()
                .id(editPost.getId())
                .title(editPost.getTitle())
                .body(editPost.getBody())
                .build();
    }
}
