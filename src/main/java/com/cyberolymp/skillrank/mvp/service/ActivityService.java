package com.cyberolymp.skillrank.mvp.service;

import com.cyberolymp.skillrank.mvp.dto.ActivityEvent;
import com.cyberolymp.skillrank.mvp.dto.ActivityRequest;
import com.cyberolymp.skillrank.mvp.dto.ActivityResponse;
import com.cyberolymp.skillrank.mvp.kafka.ActivityProducer;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {


    private final ActivityProducer activityProducer;

    public ActivityService(ActivityProducer activityProducer) {
        this.activityProducer = activityProducer;
    }

    public ActivityResponse createActivity(ActivityRequest request) {
    ActivityResponse activityResponse = new ActivityResponse();
    activityResponse.setUserId(request.getUserId());
    activityResponse.setType(request.getType());
    activityResponse.setPoints(request.getPoints());
    activityResponse.setMessage("Activity processed successfully");
    System.out.println("Activity processed successfully");

    ActivityEvent activityEvent = new ActivityEvent(
            request.getUserId(),
            request.getType(),
            request.getPoints()
    );

    activityProducer.sendActivityEvent(activityEvent);

    return activityResponse;
    }


}
