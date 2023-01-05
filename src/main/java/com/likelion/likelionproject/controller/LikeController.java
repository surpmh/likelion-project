package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.Response;
import com.likelion.likelionproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postsId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
     * 좋아요 개수
     */
    @GetMapping
    public Response<Long> count(@PathVariable Long postsId) {
        Long count = likeService.count(postsId);
        return Response.success(count);
    }

    /**
     * 좋아요 누르기
     */
    @PostMapping
    public Response<String> like(@PathVariable Long postsId, Authentication authentication) {
        String message = likeService.like(postsId, authentication.getName());
        return Response.success(message);
    }
}
