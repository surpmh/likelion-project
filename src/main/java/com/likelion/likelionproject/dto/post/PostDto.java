package com.likelion.likelionproject.dto.post;

import com.likelion.likelionproject.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto extends BaseEntity {
    private Long id;
    private String title;
    private String body;
}