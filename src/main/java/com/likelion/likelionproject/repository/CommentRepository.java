package com.likelion.likelionproject.repository;

import com.likelion.likelionproject.entity.Comment;
import com.likelion.likelionproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    Page<Comment> findByPostId(@Param(value = "post_id") Long PostId, Pageable pageable);
    void deleteAllByPost(@Param(value = "post") Post post);
}
