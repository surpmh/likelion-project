package com.likelion.likelionproject.repository;

import com.likelion.likelionproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserId(@Param(value = "user_id") Long UserId, Pageable pageable);
}
