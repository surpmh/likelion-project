package com.likelion.likelionproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity(name = "Good")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@SQLDelete(sql = "UPDATE good SET deleted_at = true WHERE id = ?")
@Where(clause = "deleted_at = false")
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean deletedAt = Boolean.FALSE;

    private boolean cancel = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public String likeUpdate(boolean cancel) {
        if (cancel) {
            this.cancel = Boolean.FALSE;
            return "좋아요를 눌렀습니다.";
        } else {
            this.cancel = Boolean.TRUE;
            return "좋아요를 취소했습니다.";
        }
    }
}
