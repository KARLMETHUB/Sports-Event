package com.karl.ms7ticketservice.dto.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Integer teamId;
    private String teamName;
    private Set<PlayerDTO> playerList;

}
