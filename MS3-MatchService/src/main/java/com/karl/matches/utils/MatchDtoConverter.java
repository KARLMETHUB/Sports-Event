package com.karl.matches.utils;

import com.karl.matches.dto.FieldDTO;
import com.karl.matches.dto.MatchDTO;
import com.karl.matches.dto.TeamDTO;
import com.karl.matches.dto.TournamentDTO;
import com.karl.matches.entity.Match;

public class MatchDtoConverter {
    private MatchDtoConverter() {}

    public static Match toEntity(MatchDTO m) {
        return Match.builder()
                .matchId(m.getMatchId())
                .tournamentId(m.getTournamentDTO().getTournamentId())
                .fieldId(m.getFieldDTO().getFieldId())
                .dateTime(m.getDateTime())
                .homeTeamId(m.getHomeTeamDto().getTeamId())
                .awayTeamId(m.getAwayTeamDto().getTeamId())
                .build();
    }


    public static MatchDTO toDto(Match m,
                                 TournamentDTO t,
                                 FieldDTO f,
                                 TeamDTO h,
                                 TeamDTO a) {
        return MatchDTO.builder()
                .matchId(m.getMatchId())
                .tournamentDTO(t)
                .fieldDTO(f)
                .dateTime(m.getDateTime())
                .homeTeamDto(h)
                .awayTeamDto(a)
                .build();
    }
}
