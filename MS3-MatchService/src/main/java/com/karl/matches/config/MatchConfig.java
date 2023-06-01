package com.karl.matches.config;

import com.karl.matches.entity.Match;
import com.karl.matches.repo.MatchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Configuration
public class MatchConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            MatchRepository matchRepository
    ) {

        return args -> {

            Match m1 = new Match(null,
                    2,
                    1,
                    LocalDateTime.of(2023, Month.JULY, 29,
                            19, 30, 40),
                    1,
                    2
                    );

            Match m2 = new Match(null,
                    1,
                    3,
                    LocalDateTime.of(2023, Month.AUGUST, 12,
                            13, 00, 00),
                    3,
                    4
            );

            Match m3 = new Match(null,
                    3,
                    1,
                    LocalDateTime.of(2023, Month.DECEMBER, 25,
                            8, 00, 00),
                    1,
                    4
            );

            matchRepository.saveAll(
                    List.of(m1,m2,m3)
            );
        };
    }
}
