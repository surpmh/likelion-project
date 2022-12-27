package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.*;
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
    @PostMapping("")
    public Response<PostResponse> create(@RequestBody PostWriteRequest postCreateRequest) {
        PostDto postDto = postService.create(postCreateRequest);
        return Response.success(new PostResponse("포스트 등록 완료", postDto.getId()));
    }

    // 포스트 수정
    @PostMapping("/{id}")
    public Response<PostResponse> edit(@PathVariable Long id, @RequestBody PostWriteRequest postWriteRequest) {
        PostDto postDto = postService.edit(id, postWriteRequest);
        return Response.success(new PostResponse("포스트 수정 완료", postDto.getId()));
    }

    // 포스트 삭제
    @DeleteMapping("/{id}")
    public Response<PostResponse> delete(@PathVariable Long id) {
        postService.delete(id);
        return Response.success(new PostResponse("포스트 삭제 완료", id));
    }
}
