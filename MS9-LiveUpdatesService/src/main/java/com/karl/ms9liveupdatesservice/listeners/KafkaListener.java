package com.karl.ms9liveupdatesservice.listeners;

import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    // TODO: 5/27/2023 : Update groupId 
    @org.springframework.kafka.annotation.KafkaListener(
            topics = "live-updates-basketball",
            groupId = "groupId"
    )
    void listener(String data) {
        System.out.println("Listener received :" + data + ".");
    }
}
