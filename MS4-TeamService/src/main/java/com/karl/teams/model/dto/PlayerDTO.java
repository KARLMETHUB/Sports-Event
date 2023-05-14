package com.karl.teams.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    Integer playerId;
    String firstName;
    String lastName;
    String country;
    Integer teamId;

}