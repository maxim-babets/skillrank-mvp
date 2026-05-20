package com.cyberolymp.skillrank.mvp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse {
    private Long userId;
    private Long rank;
    private Double score;
}
