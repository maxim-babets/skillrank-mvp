package com.cyberolymp.skillrank.mvp.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHistoryResponse {
    private Long id;
    private Long userId;
    private String type;
    private Integer points;
    private LocalDateTime createdAt;
}
