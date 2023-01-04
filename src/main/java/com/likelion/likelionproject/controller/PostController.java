package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.*;
import com.likelion.likelionproject.dto.post.*;
import com.likelion.likelionproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    /**
     * 마이 피드
     */
    @GetMapping("/my")
    public Response<Page<PostDetailResponse>> my(@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        Page<PostDetailResponse> postDetailResponses = postService.my(authentication.getName(), pageable);
        return Response.success(postDetailResponses);
    }

    /**
     * 포스트 리스트
     */
    @GetMapping("")
    public Response<PostPageResponse> list(@PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable) {
        PostPageResponse postPageResponse = postService.list(pageable);
        return Response.success(postPageResponse);
    }

    /**
     * 포스트 상세
     */
    @GetMapping("/{id}")
    public Response<PostDetailResponse> detail(@PathVariable Long id) {
        PostDetailResponse PostReadResponse = postService.detail(id);
        return Response.success(PostReadResponse);
    }

    /**
     * 포스트 등록
     */
    @PostMapping("")
    public Response<PostResponse> create(@RequestBody PostRequest postWriteRequest, Authentication authentication) {
        PostDto postDto = postService.create(postWriteRequest, authentication.getName());
        return Response.success(new PostResponse("포스트 등록 완료", postDto.getId()));
    }

    /**
     * 포스트 수정
     */
    @PutMapping("/{id}")
    public Response<PostResponse> edit(@PathVariable Long id, @RequestBody PostRequest postWriteRequest, Authentication authentication) {
        PostDto postDto = postService.edit(id, postWriteRequest, authentication.getName());
        return Response.success(new PostResponse("포스트 수정 완료", postDto.getId()));
    }

    /**
     * 포스트 삭제
     */
    @DeleteMapping("/{id}")
    public Response<PostResponse> delete(@PathVariable Long id, Authentication authentication) {
        postService.delete(id, authentication.getName());
        return Response.success(new PostResponse("포스트 삭제 완료", id));
    }
}
