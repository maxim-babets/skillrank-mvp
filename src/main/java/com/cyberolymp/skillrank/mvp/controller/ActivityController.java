package com.cyberolymp.skillrank.mvp.controller;

import com.cyberolymp.skillrank.mvp.dto.ActivityRequest;
import com.cyberolymp.skillrank.mvp.dto.ActivityResponse;
import com.cyberolymp.skillrank.mvp.dto.LeaderboardResponse;
import com.cyberolymp.skillrank.mvp.leaderboard.LeaderboardService;
import com.cyberolymp.skillrank.mvp.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class ActivityController {

    private final ActivityService activityService;
    private final LeaderboardService leaderboardService;

    public ActivityController(ActivityService activityService, LeaderboardService leaderboardService) {
        this.activityService = activityService;
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/api/activities/")
    public ActivityResponse createActivity(@Valid @RequestBody ActivityRequest request){
        return activityService.createActivity(request);
    }

    @GetMapping("/api/leaderboard/top/")
    public List<LeaderboardResponse> getTopUser(){
       return leaderboardService.getTopUsers();
    }
}
