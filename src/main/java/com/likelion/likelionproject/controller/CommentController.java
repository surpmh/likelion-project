package com.likelion.likelionproject.controller;

import com.likelion.likelionproject.dto.Response;
import com.likelion.likelionproject.dto.comment.CommentDto;
import com.likelion.likelionproject.dto.comment.CommentRequest;
import com.likelion.likelionproject.dto.comment.CommentResponse;
import com.likelion.likelionproject.service.CommentService;
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
@RequestMapping("/api/v1/{postsId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 조회
     */
    @GetMapping("")
    public Response<Page<CommentDto>> list(@PathVariable Long postsId, @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentDto> commentDto = commentService.list(postsId, pageable);
        return Response.success(commentDto);
    }


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
        CommentDto commentDto = commentService.edit(postsId, id, commentRequest, authentication.getName());
        return Response.success(commentDto);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{id}")
    public Response<CommentResponse> delete(@PathVariable Long id, Authentication authentication) {
        commentService.delete(id, authentication.getName());
        return Response.success(new CommentResponse("댓글 삭제 완료", id));
    }
}
