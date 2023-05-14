package com.karl.teams.utils;

import com.karl.teams.model.dto.PlayerDTO;
import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.model.entities.Team;

import java.util.Set;

public class TeamDtoConverter {

    private TeamDtoConverter() {}

    public static Team toEntity(TeamDTO t) {
        return Team.builder()
                .teamId(t.getTeamId())
                .teamName(t.getTeamName())
                .players(t.getPlayerList())
                .build();
    }

    public static TeamDTO toDto(Team t) {
        return TeamDTO.builder()
                .teamId(t.getTeamId())
                .teamName(t.getTeamName())
                .playerList(t.getPlayers())
                .build();
    }

    public static TeamDTO toDto(Team t, Set<PlayerDTO> p) {
        return TeamDTO.builder()
                .teamId(t.getTeamId())
                .teamName(t.getTeamName())
                .playerList(p)
                .build();
    }
}
