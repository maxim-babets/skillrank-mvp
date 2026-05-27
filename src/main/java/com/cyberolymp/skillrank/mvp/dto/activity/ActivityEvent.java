package com.cyberolymp.skillrank.mvp.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEvent {
    private Long userId;
    private String type;
    private Integer points;
}
