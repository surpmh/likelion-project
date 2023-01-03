package com.likelion.likelionproject.repository;

import com.likelion.likelionproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
