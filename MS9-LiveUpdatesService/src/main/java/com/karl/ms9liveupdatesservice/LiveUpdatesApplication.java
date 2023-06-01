package com.karl.ms9liveupdatesservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karl.ms9liveupdatesservice.constants.Events;
import com.karl.ms9liveupdatesservice.dto.BasketballGameEvent;
import com.karl.ms9liveupdatesservice.dto.GameEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

// TODO: 5/27/2023 : Create bash script for kafka 
@SpringBootApplication
public class LiveUpdatesApplication
{

    public static void main( String[] args ) {
        SpringApplication.run(LiveUpdatesApplication.class,args);
    }
    public static final String topic = "live-updates-basketball";

    /*@Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, GameEvent> liveUpdateTemplate,ObjectMapper objectMapper) {
        return args -> {
            liveUpdateTemplate
                    .send(
                            topic,
                            BasketballGameEvent
                                    .builder()
                                    .event(Events.GAME_START)
                                    .homeTeamName("LAL")
                                    .awayTeamName("GSW")
                                    .timestamp(LocalDateTime.now())
                                    .build()
                            );

            for (int i = 0; i < 123; i++) {
                int j = i+1;

                liveUpdateTemplate
                        .send(topic,
                                BasketballGameEvent
                                    .builder()
                                    .event(Events.SCORE_UPDATE)
                                    .homeTeamName("LAL")
                                    .awayTeamName("GSW")
                                    .awayTeamScore(i)
                                    .homeTeamScore(j)
                                    .timestamp(LocalDateTime.now())
                                    .build());
            }

            liveUpdateTemplate
                    .send(
                            topic,BasketballGameEvent
                                    .builder()
                                    .event(Events.GAME_END)
                                    .homeTeamName("LAL")
                                    .awayTeamName("GSW")
                                    .timestamp(LocalDateTime.now())
                                    .build());
        };
    }*/

}
