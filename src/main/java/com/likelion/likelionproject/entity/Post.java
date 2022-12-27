package com.likelion.likelionproject.entity;

import com.likelion.likelionproject.dto.PostWriteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private String body;

    public void postEdit(PostWriteRequest postWriteRequest) {
        this.title = postWriteRequest.getTitle();
        this.body = postWriteRequest.getBody();
    }
}
