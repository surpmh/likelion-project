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
    @PutMapping("/{id}")
    public Response<CommentDto> edit(@PathVariable Long postsId, @PathVariable Long id, @RequestBody CommentRequest commentRequest, Authentication authentication) {
        log.info(String.valueOf(postsId), id);
        CommentDto commentDto = commentService.edit(postsId, id, commentRequest, authentication.getName());
        return Response.success(commentDto);
    }
}
