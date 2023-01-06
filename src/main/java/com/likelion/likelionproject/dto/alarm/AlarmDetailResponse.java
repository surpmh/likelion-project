package com.likelion.likelionproject.dto.alarm;

import com.likelion.likelionproject.entity.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDetailResponse {
    private Long id;
    private String alarmType;
    private Long fromUserId;
    private Long targetId;
    private String text;
    private LocalDateTime createdAt;

    public static AlarmDetailResponse fromEntity(Alarm savedAlarm) {
        return AlarmDetailResponse.builder()
                .id(savedAlarm.getId())
                .alarmType(savedAlarm.getAlarmType())
                .fromUserId(savedAlarm.getFromUserId())
                .targetId(savedAlarm.getTargetId())
                .text(savedAlarm.getText())
                .createdAt(savedAlarm.getCreatedAt())
                .build();
    }
}
