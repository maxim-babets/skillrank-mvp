package com.cyberolymp.skillrank.mvp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponse {
    private Long userId;
    private String type;
    private Integer points;
    private String message;
}
