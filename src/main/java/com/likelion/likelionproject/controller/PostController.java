package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.PostCreateRequest;
import com.likelion.likelionproject.dto.PostCreateResponse;
import com.likelion.likelionproject.dto.PostDto;
import com.likelion.likelionproject.dto.Response;
import com.likelion.likelionproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 포스트 등록
    @PostMapping("/create")
    public Response<PostCreateResponse> create(@RequestBody PostCreateRequest postCreateRequest) {
        PostDto postDto = postService.create(postCreateRequest);
        return Response.success(new PostCreateResponse("포스트 등록 완료", postDto.getId()));
    }
}
