package com.cyberolymp.skillrank.mvp.service;

import com.cyberolymp.skillrank.mvp.domain.Activity;
import com.cyberolymp.skillrank.mvp.domain.User;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityEvent;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityHistoryResponse;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityRequest;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityResponse;
import com.cyberolymp.skillrank.mvp.exception.UserNotFoundException;
import com.cyberolymp.skillrank.mvp.kafka.ActivityProducer;
import com.cyberolymp.skillrank.mvp.repository.ActivityRepository;
import com.cyberolymp.skillrank.mvp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityService {


    private final ActivityProducer activityProducer;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityService(ActivityProducer activityProducer, ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityProducer = activityProducer;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public ActivityResponse createActivity(ActivityRequest request) {
    ActivityResponse activityResponse = new ActivityResponse();
    activityResponse.setUserId(request.getUserId());
    activityResponse.setType(request.getType());
    activityResponse.setPoints(request.getPoints());
    activityResponse.setMessage("Activity processed successfully");
    System.out.println("Activity processed successfully");

    Activity activity = new Activity();

    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    activity.setUser(user);
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

//    public List<ActivityHistoryResponse> getAllActivities(){
//    List<Activity> activities = activityRepository.findAll();
//    return activities.stream()
//            .map(activity -> new ActivityHistoryResponse(
//                    activity.getId(),
//                    activity.getUserId(),
//                    activity.getType(),
//                    activity.getPoints(),
//                    activity.getCreatedAt()
//            ))
//            .toList();
//    }

    public Page<ActivityHistoryResponse> getAllActivities(Pageable pageable){
        Page<Activity> activities = activityRepository.findAll(pageable);
        return activities.map(activity -> new ActivityHistoryResponse(
                activity.getId(),
                activity.getUser().getId(),
                activity.getType(),
                activity.getPoints(),
                activity.getCreatedAt()
        ));
    }
}
