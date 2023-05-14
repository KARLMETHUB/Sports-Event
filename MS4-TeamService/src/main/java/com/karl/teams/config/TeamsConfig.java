package com.karl.teams.config;

import com.karl.teams.repository.TeamsRepository;
import com.karl.teams.model.entities.Team;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
public class TeamsConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            TeamsRepository teamsRepository
    ) {

        return args -> {

            Team collabera = new Team(null,"Team Collabera", Collections.emptySet());
            Team lakers = new Team(null,"LA Lakers", Collections.emptySet());
            Team bostonCeltics = new Team(null,"Boston Celtics", Collections.emptySet());
            Team cognixia = new Team(null,"Team Cognixia", Collections.emptySet());

            teamsRepository.saveAll(
                List.of(collabera,lakers,bostonCeltics,cognixia)
            );
        };
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
