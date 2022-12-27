package com.likelion.likelionproject.repository;

import com.likelion.likelionproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long id);
}
