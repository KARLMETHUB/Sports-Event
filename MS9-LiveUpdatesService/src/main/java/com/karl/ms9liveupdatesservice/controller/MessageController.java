package com.karl.ms9liveupdatesservice.controller;

import com.karl.ms9liveupdatesservice.dto.BasketballGameEvent;
import com.karl.ms9liveupdatesservice.dto.GameEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    private final KafkaTemplate<String, GameEvent> kafkaTemplate;

    public MessageController(KafkaTemplate<String, GameEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/publish")
    public void publish(@RequestBody BasketballGameEvent gameEvent) {
        kafkaTemplate.send("live-updates-basketball",gameEvent);
    }
}
