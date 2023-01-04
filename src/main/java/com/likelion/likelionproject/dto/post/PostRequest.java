package com.likelion.likelionproject.dto.post;

import com.likelion.likelionproject.entity.Post;
import com.likelion.likelionproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String body;

    public Post toEntity(User user) {
        return Post.builder()
                .title(this.title)
                .body(this.body)
                .user(user)
                .build();
    }
}
