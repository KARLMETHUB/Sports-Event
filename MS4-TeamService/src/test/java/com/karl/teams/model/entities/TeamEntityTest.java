package com.karl.teams.model.entities;

import com.karl.teams.model.dto.PlayerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamEntityTest {

    Team noArgsTeam;
    Team allArgsTeam;
    Team builderTeam;
    Integer expectedId = 1;
    String expectedName = "Test";

    List<PlayerDTO> playerDTOS = new ArrayList<>();


    @BeforeEach
    void setUp() {
        noArgsTeam = new Team();
        playerDTOS.add(new PlayerDTO(1,"Test fname","Test lname","PH",expectedId));
        playerDTOS.add(new PlayerDTO(2,"Test fname2","Test lname2","PH",expectedId));

        allArgsTeam = new Team(expectedId,expectedName, new HashSet<>(playerDTOS));

        builderTeam = Team.builder()
                .teamId(2)
                .teamName("Team 2")
                .players(Collections.emptySet())
                .build();
    }
    @Test
    void testConstructor() {

        assertNull(noArgsTeam.getTeamId());
        assertNull(noArgsTeam.getTeamName());
        assertNull(noArgsTeam.getPlayers());

        assertEquals(expectedId,allArgsTeam.getTeamId());
        assertEquals(expectedName,allArgsTeam.getTeamName());
        assertEquals(playerDTOS.size(),allArgsTeam.getPlayers().size());
    }

    @Test
    void testEqualsAndHashCode() {

        noArgsTeam.setTeamId(expectedId);
        noArgsTeam.setTeamName(expectedName);
        noArgsTeam.setPlayers(new HashSet<>(playerDTOS));

        assertEquals(noArgsTeam.getTeamName(),allArgsTeam.getTeamName());
        assertEquals(noArgsTeam.getPlayers().size(),allArgsTeam.getPlayers().size());

        assertNotEquals(allArgsTeam, builderTeam);
        assertNotEquals(allArgsTeam.hashCode(), builderTeam.hashCode());
        assertNotEquals(allArgsTeam.getTeamId(),builderTeam.getTeamId());
        assertNotEquals(allArgsTeam.getTeamName(),builderTeam.getTeamName());
        assertNotEquals(allArgsTeam.getPlayers().size(),builderTeam.getPlayers().size());
    }

}
