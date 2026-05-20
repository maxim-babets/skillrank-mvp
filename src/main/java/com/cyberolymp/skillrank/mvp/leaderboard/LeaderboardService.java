package com.cyberolymp.skillrank.mvp.leaderboard;

import com.cyberolymp.skillrank.mvp.dto.ActivityEvent;
import com.cyberolymp.skillrank.mvp.dto.LeaderboardResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LeaderboardService {

    private final StringRedisTemplate redisTemplate;
    private static final String LEADERBOARD_KEY = "leaderboard";

    public LeaderboardService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void updateLeaderboard(ActivityEvent event){
        redisTemplate.opsForZSet().add(LEADERBOARD_KEY,
                event.getUserId().toString(),
                event.getPoints());
    }

//    public Map<String,Double> getTopUsers(){
//        Map<String,Double> top = new HashMap<>();
//        Set<ZSetOperations.TypedTuple<String>> leaderboardRange= redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY,0,20);
//        leaderboardRange.forEach(stringTypedTuple -> top.put(
//                stringTypedTuple.getValue(),
//                stringTypedTuple.getScore()));
//        return top;
//    }


    public List<LeaderboardResponse> getTopUsers(){
        List<LeaderboardResponse> topUsers = new LinkedList<>();
        Set<ZSetOperations.TypedTuple<String>> leaderboardRange= redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY,0,20);
        long rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : leaderboardRange){
            LeaderboardResponse leaderboardResponse = new LeaderboardResponse();
            leaderboardResponse.setUserId(Long.valueOf(tuple.getValue()));
            leaderboardResponse.setScore(tuple.getScore());
            leaderboardResponse.setRank(rank);
            topUsers.add(leaderboardResponse);
            rank++;
        }
        return topUsers;
    }
}
