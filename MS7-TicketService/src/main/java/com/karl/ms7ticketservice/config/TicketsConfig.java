package com.karl.ms7ticketservice.config;

import com.karl.ms7ticketservice.entity.Ticket;
import com.karl.ms7ticketservice.repository.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TicketsConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            TicketRepository ticketRepository) {

        return args -> {

            Ticket t = new Ticket(null,"Giyu Tomioka",200.55F,1,null);
            Ticket t2 = new Ticket(null,"Mitsuri Kanroji",200.55F,1,null);
            Ticket t3 = new Ticket(null,"Nezuko Kamado",100F,2,null);
            Ticket t4 = new Ticket(null,"Muichiro Tokito",5478F,2,null);
            Ticket t5 = new Ticket(null,"Shinobu Kocho",5478F,3,null);
            Ticket t6 = new Ticket(null,"Muzan Kibutsuji",5478F,3,null);

            ticketRepository.saveAll(
                    List.of(t,t2,t3,t4,t5,t6));
        };
    }
}
