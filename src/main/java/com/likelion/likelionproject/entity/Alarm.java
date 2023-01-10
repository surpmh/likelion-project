package com.likelion.likelionproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comment SET deleted_at = true WHERE id = ?")
@Where(clause = "deleted_at = false")
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alarmType;
    private Long fromUserId;
    private Long targetId;
    private String text;
    private boolean deletedAt = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
