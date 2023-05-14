package com.karl.ms5playerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private Integer playerId;
    private String firstName;
    private String lastName;
    private String country;
    private Integer teamId;
}
