package com.likelion.likelionproject.dto.alarm;

import com.likelion.likelionproject.entity.Alarm;
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
public class AlarmRequest {
    private String alarmType;
    private String text;

    public Alarm toEntity(Post post, User fromUser, User targetUser) {
        return Alarm.builder()
                .alarmType(this.alarmType)
                .fromUserId(fromUser.getId())
                .targetId(targetUser.getId())
                .user(targetUser)
                .post(post)
                .text(this.text)
                .build();
    }
}
