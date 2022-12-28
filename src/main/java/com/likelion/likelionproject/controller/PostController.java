package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.*;
import com.likelion.likelionproject.dto.post.PostDto;
import com.likelion.likelionproject.dto.post.PostReadResponse;
import com.likelion.likelionproject.dto.post.PostResponse;
import com.likelion.likelionproject.dto.post.PostWriteRequest;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
        PostReadResponse PostReadResponse = postService.detail(id);
        return Response.success(PostReadResponse);
    }

    // 포스트 등록
    @PostMapping("")
    public Response<PostResponse> create(@RequestBody PostWriteRequest postCreateRequest, Authentication authentication) {
        PostDto postDto = postService.create(postCreateRequest, authentication.getName());
        return Response.success(new PostResponse("포스트 등록 완료", postDto.getId()));
    }

    // 포스트 수정
    @PutMapping("/{id}")
    public Response<PostResponse> edit(@PathVariable Long id, @RequestBody PostWriteRequest postWriteRequest, Authentication authentication) {
        PostDto postDto = postService.edit(id, postWriteRequest, authentication.getName());
        return Response.success(new PostResponse("포스트 수정 완료", postDto.getId()));
    }

    // 포스트 삭제
    @DeleteMapping("/{id}")
    public Response<PostResponse> delete(@PathVariable Long id, Authentication authentication) {
        postService.delete(id, authentication.getName());
        return Response.success(new PostResponse("포스트 삭제 완료", id));
    }
}
