package com.karl.ms8tournamentservice.utils;

import com.karl.ms8tournamentservice.dto.TournamentDTO;
import com.karl.ms8tournamentservice.entities.Tournament;

public class TournamentDtoConverter {

    private TournamentDtoConverter() {}

    public static Tournament toEntity(TournamentDTO t) {

        return Tournament.builder()
                .tournamentId(t.getTournamentId())
                .tournamentName(t.getTournamentName())
                .tournamentStyle(t.getTournamentStyle())
                .sportsCategory(t.getSportsCategory())
                .build();
    }

    public static TournamentDTO toDto(Tournament t) {

        return TournamentDTO.builder()
                .tournamentId(t.getTournamentId())
                .tournamentName(t.getTournamentName())
                .tournamentStyle(t.getTournamentStyle())
                .sportsCategory(t.getSportsCategory())
                .build();
    }

}
