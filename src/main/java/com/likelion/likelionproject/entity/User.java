package com.likelion.likelionproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted_at = true WHERE id = ?")
@Where(clause = "deleted_at = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;
    private String password;
    private boolean deletedAt = Boolean.FALSE;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> post;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> Comment;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> Like;
}
