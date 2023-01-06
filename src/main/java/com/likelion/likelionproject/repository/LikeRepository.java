package com.likelion.likelionproject.repository;

import com.likelion.likelionproject.entity.Like;
import com.likelion.likelionproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByPostId(@Param(value = "post_id") Long postId);
    Optional<Like> findByPostIdAndUserId(@Param(value = "post_id") Long postId, @Param(value = "user_id") Long userId);
    void deleteAllByPost(@Param(value = "post") Post post);
}
