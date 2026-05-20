package com.cyberolymp.skillrank.mvp.kafka;

import com.cyberolymp.skillrank.mvp.dto.ActivityEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActivityProducer {

    private final KafkaTemplate<String, ActivityEvent> kafkaTemplate;

    public ActivityProducer(KafkaTemplate<String, ActivityEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendActivityEvent (ActivityEvent event){
        kafkaTemplate.send("activity-events", event)
                .whenComplete((result, ex)->{
                    if(ex!=null){
                        System.out.println("Kafka send failed: " + ex.getMessage());
                    }else{
                        System.out.println("Kafka send success: "+ event);
                        System.out.println("topic: " + result.getRecordMetadata().topic());
                        System.out.println("partition: " + result.getRecordMetadata().partition());
                        System.out.println("offset: " + result.getRecordMetadata().offset());
                    }
                });
    }
}
