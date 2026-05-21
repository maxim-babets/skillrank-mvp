package com.cyberolymp.skillrank.mvp.service;

import com.cyberolymp.skillrank.mvp.domain.Activity;
import com.cyberolymp.skillrank.mvp.dto.ActivityEvent;
import com.cyberolymp.skillrank.mvp.dto.ActivityHistoryResponse;
import com.cyberolymp.skillrank.mvp.dto.ActivityRequest;
import com.cyberolymp.skillrank.mvp.dto.ActivityResponse;
import com.cyberolymp.skillrank.mvp.kafka.ActivityProducer;
import com.cyberolymp.skillrank.mvp.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {


    private final ActivityProducer activityProducer;
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityProducer activityProducer, ActivityRepository activityRepository) {
        this.activityProducer = activityProducer;
        this.activityRepository = activityRepository;
    }

    public ActivityResponse createActivity(ActivityRequest request) {
    ActivityResponse activityResponse = new ActivityResponse();
    activityResponse.setUserId(request.getUserId());
    activityResponse.setType(request.getType());
    activityResponse.setPoints(request.getPoints());
    activityResponse.setMessage("Activity processed successfully");
    System.out.println("Activity processed successfully");

    Activity activity = new Activity();
    activity.setUserId(request.getUserId());
    activity.setType(request.getType());
    activity.setPoints(request.getPoints());
    activity.setCreatedAt(LocalDateTime.now());

    activityRepository.save(activity);

        ActivityEvent activityEvent = new ActivityEvent(
                request.getUserId(),
                request.getType(),
                request.getPoints()
        );
    activityProducer.sendActivityEvent(activityEvent);

    return activityResponse;
    }

    public List<ActivityHistoryResponse> getAllActivities(){
    List<Activity> activities = activityRepository.findAll();
    return activities.stream()
            .map(activity -> new ActivityHistoryResponse(
                    activity.getId(),
                    activity.getUserId(),
                    activity.getType(),
                    activity.getPoints(),
                    activity.getCreatedAt()
            ))
            .toList();
    }
}
