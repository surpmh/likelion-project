package com.likelion.likelionproject.dto.like;

import com.likelion.likelionproject.entity.Like;
import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.entity.User;
import lombok.Builder;

@Builder
public class LikeRequest {
    public static Like toEntity(User user, Post post) {
        return Like.builder()
                .user(user)
                .post(post)
                .build();
    }
}
