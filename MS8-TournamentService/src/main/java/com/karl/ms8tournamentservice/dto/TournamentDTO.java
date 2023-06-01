package com.karl.ms8tournamentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDTO {

    private Integer tournamentId;

    private String tournamentName;

    private String sportsCategory;

    private String tournamentStyle;

}
