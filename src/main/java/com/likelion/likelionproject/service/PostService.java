package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.PostCreateRequest;
import com.likelion.likelionproject.dto.PostDto;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostDto create(PostCreateRequest request) {
        Post savedPost = postRepository.save(request.toEntity());

        return PostDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .build();
    }
}
