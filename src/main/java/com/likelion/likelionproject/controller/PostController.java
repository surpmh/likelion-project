package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.PostCreateRequest;
import com.likelion.likelionproject.dto.PostResponse;
import com.likelion.likelionproject.dto.PostDto;
import com.likelion.likelionproject.dto.Response;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 포스트 상세
    @GetMapping("/{id}")
    public Response<PostReadResponse> detail(@PathVariable Long id) {
        Post post = postService.detail(id);
        PostReadResponse response = PostReadResponse.fromEntity(post);
        return Response.success(response);
    }

    // 포스트 등록
    @PostMapping("/create")
    public Response<PostResponse> create(@RequestBody PostCreateRequest postCreateRequest) {
        PostDto postDto = postService.create(postCreateRequest);
        return Response.success(new PostResponse("포스트 등록 완료", postDto.getId()));
    }
}
