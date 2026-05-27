package com.cyberolymp.skillrank.mvp;

import com.cyberolymp.skillrank.mvp.domain.Activity;
import com.cyberolymp.skillrank.mvp.domain.User;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityEvent;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityHistoryResponse;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityRequest;
import com.cyberolymp.skillrank.mvp.dto.activity.ActivityResponse;
import com.cyberolymp.skillrank.mvp.kafka.ActivityProducer;
import com.cyberolymp.skillrank.mvp.repository.ActivityRepository;
import com.cyberolymp.skillrank.mvp.repository.UserRepository;
import com.cyberolymp.skillrank.mvp.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @Mock
    ActivityRepository activityRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ActivityProducer activityProducer;

    @InjectMocks
    ActivityService activityService;

    @Test
    public void shouldCreateActivitySuccessfullly(){
        ActivityRequest activityRequest = new ActivityRequest();
        activityRequest.setUserId(1L);
        activityRequest.setType("Post_Created");
        activityRequest.setPoints(5);

        User user = new User();
        user.setId(1L);

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

       ActivityResponse response = activityService.createActivity(activityRequest);
       assertEquals(1L,response.getUserId());
       assertEquals("Post_Created",response.getType());
       assertEquals(5,response.getPoints());

        Mockito.verify(activityRepository).save(any(Activity.class));
        Mockito.verify(activityProducer).sendActivityEvent(any(ActivityEvent.class));
    }

    @Test
    public void getAllActivities_ShouldReturnPaginatedActivityHistory(){
        User user = new User();
        user.setId(1L);
        Activity activity = new Activity(user,1L,"Post_Created",5, LocalDateTime.now());
        Page<Activity> pages = new PageImpl<>(List.of(activity));
        Pageable pageable = Pageable.unpaged();

        Mockito.when(activityRepository.findAll(pageable))
                .thenReturn(pages);

        Page<ActivityHistoryResponse> result = activityService.getAllActivities(pageable);
        assertEquals(1,result.getContent().size());
        assertEquals(1L,result.getContent().get(0).getUserId());
        assertEquals("Post_Created",result.getContent().get(0).getType());

        Mockito.verify(activityRepository).findAll(pageable);
    }
}
