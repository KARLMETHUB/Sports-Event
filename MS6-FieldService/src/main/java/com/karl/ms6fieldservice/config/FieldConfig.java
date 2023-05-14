package com.karl.ms6fieldservice.config;

import com.karl.ms6fieldservice.entity.Field;
import com.karl.ms6fieldservice.repository.FieldRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FieldConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            FieldRepository fieldRepository
    ) {

        return args -> {

            Field field = new Field(null,"Madison Square Garden","New York City, New York",19812);
            Field field2 = new Field(null,"Staples Center","Los Angeles, California",18997);
            Field field3 = new Field(null,"Manchester Arena","United Kingdom, California",21000);

            fieldRepository.saveAll(
                    List.of(field,field2,field3)
            );
        };
    }

}
