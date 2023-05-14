package com.karl.ms8tournamentservice.config;

import com.karl.ms8tournamentservice.entities.Tournament;
import com.karl.ms8tournamentservice.repository.TournamentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TournamentsConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            TournamentRepository tournamentRepository) {

        return args -> {

            Tournament t = new Tournament(null,"FIFA","Multilevel Tournament","Soccer");
            Tournament t2 = new Tournament(null,"NBA","Play-In Tournament","Basketball");
            Tournament t3 = new Tournament(null,"MLB","Play-In Tournament","Baseball");

            tournamentRepository.saveAll(
                    List.of(t,t2,t3));
        };
    }
}