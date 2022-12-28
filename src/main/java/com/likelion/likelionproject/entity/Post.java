package com.likelion.likelionproject.entity;

import com.likelion.likelionproject.dto.post.PostWriteRequest;
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
    private Long id;

    private String title;
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void postEdit(PostWriteRequest postWriteRequest) {
        this.title = postWriteRequest.getTitle();
        this.body = postWriteRequest.getBody();
    }
}
