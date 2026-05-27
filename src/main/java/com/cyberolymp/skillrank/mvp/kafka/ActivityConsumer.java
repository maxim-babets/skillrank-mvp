package com.cyberolymp.skillrank.mvp.kafka;

import com.cyberolymp.skillrank.mvp.dto.activity.ActivityEvent;
import com.cyberolymp.skillrank.mvp.leaderboard.LeaderboardService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ActivityConsumer {

    private final LeaderboardService leaderboardService;

    public ActivityConsumer(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @KafkaListener(
            topics = "activity-events",
            groupId = "skillrank-group"
    )

    public void consume(ActivityEvent event){
        leaderboardService.updateLeaderboard(event);
        System.out.println("CONSUMER RECEIVED EVENT: " + event);
    }

}
