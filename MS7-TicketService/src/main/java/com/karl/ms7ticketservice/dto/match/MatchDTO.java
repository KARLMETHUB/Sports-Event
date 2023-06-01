package com.karl.ms7ticketservice.dto.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {

    private Integer matchId;

    private FieldDTO fieldDTO;

    private TournamentDTO tournamentDTO;

    private LocalDateTime dateTime;

    private TeamDTO homeTeamDto;

    private TeamDTO awayTeamDto;

}
