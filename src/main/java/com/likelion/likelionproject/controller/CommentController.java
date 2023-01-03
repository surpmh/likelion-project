package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.Response;
import com.likelion.likelionproject.dto.comment.CommentDto;
import com.likelion.likelionproject.dto.comment.CommentRequest;
import com.likelion.likelionproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/{postsId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping("")
    public Response<CommentDto> create(@PathVariable Long postsId, @RequestBody CommentRequest commentRequest, Authentication authentication) {
        CommentDto commentDto = commentService.create(postsId, commentRequest, authentication.getName());
        return Response.success(commentDto);
    }

    /**
     * 댓글 수정
     */
}
