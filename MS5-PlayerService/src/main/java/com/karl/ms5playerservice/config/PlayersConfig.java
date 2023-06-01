package com.karl.ms5playerservice.config;

import com.karl.ms5playerservice.entity.Player;
import com.karl.ms5playerservice.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PlayersConfig {

    @Bean
    CommandLineRunner commandLineRunner2(
            PlayerRepository playersRepository) {

        return args -> {
            Player p = new Player(null,"Lebron","James","USA",2);
            Player p1 = new Player(null,"Anthony","Davis","USA",2);
            Player p2 = new Player(null,"Kobe","Bryant","USA",2);
            Player p3 = new Player(null,"Jordan","Poole","USA",3);
            Player p4 = new Player(null,"Jason","Tatum","USA",3);
            Player p5 = new Player(null,"Karl","Mirafuente","PH",1);
            Player p6 = new Player(null,"RM","Bautista","PH",1);
            Player p7 = new Player(null,"Subbu","N/a","IND",4);
            Player p8 = new Player(null,"Murtaza","N/a","IND",4);

            playersRepository.saveAll(
                List.of(p,p1,p2,p3,p4,p5,p6,p7,p8)
            );
        };

    }
}

