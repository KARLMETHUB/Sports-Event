package com.karl.matches.entity;

import com.karl.matches.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MatchEntityTest {

    Match noArgsMatch;
    Match allArgsMatch;
    Match builderMatch;
    Integer expectedId = 1, expectedFieldId = 1,
            expectedTournamentId = 1,expectedHomeId = 1,
            expectedAwayId = 2;
    LocalDateTime expectedDateTime = LocalDateTime.of(2023, Month.JULY, 29, 19, 30, 40);

    @BeforeEach
    void setUp() {
        noArgsMatch = new Match();

        allArgsMatch = new Match(
                expectedId,
                expectedFieldId,
                expectedTournamentId,
                expectedDateTime,
                expectedHomeId,
                expectedAwayId);

        builderMatch = Match.builder()
                .matchId(null)
                .fieldId(expectedFieldId)
                .tournamentId(expectedTournamentId)
                .dateTime(expectedDateTime)
                .homeTeamId(expectedHomeId)
                .awayTeamId(expectedAwayId)
                .build();
    }

    @Test
    void testConstructor() {

        assertNull(noArgsMatch.getMatchId());
        assertNull(noArgsMatch.getTournamentId());
        assertNull(noArgsMatch.getDateTime());
        assertNull(noArgsMatch.getHomeTeamId());
        assertNull(noArgsMatch.getAwayTeamId());

        assertEquals(expectedId,allArgsMatch.getMatchId());
        assertEquals(expectedFieldId,allArgsMatch.getFieldId());
        assertEquals(expectedTournamentId,allArgsMatch.getTournamentId());
        assertEquals(expectedHomeId,allArgsMatch.getHomeTeamId());
        assertEquals(expectedAwayId,allArgsMatch.getAwayTeamId());
        assertEquals(expectedDateTime,allArgsMatch.getDateTime());
    }

    @Test
    void testEqualsAndHashCode() {

        noArgsMatch.setMatchId(expectedId);
        noArgsMatch.setFieldId(expectedFieldId);
        noArgsMatch.setTournamentId(expectedTournamentId);
        noArgsMatch.setDateTime(expectedDateTime);
        noArgsMatch.setHomeTeamId(expectedHomeId);
        noArgsMatch.setAwayTeamId(expectedAwayId);


        assertEquals(noArgsMatch.getMatchId(),allArgsMatch.getMatchId());
        assertEquals(noArgsMatch.getFieldId(),allArgsMatch.getFieldId());
        assertEquals(noArgsMatch.getTournamentId(),allArgsMatch.getTournamentId());
        assertEquals(noArgsMatch.getDateTime(),allArgsMatch.getDateTime());
        assertEquals(noArgsMatch.getHomeTeamId(),allArgsMatch.getHomeTeamId());
        assertEquals(noArgsMatch.getAwayTeamId(),allArgsMatch.getAwayTeamId());

        assertNotEquals(allArgsMatch, builderMatch);
        assertNotEquals(allArgsMatch.hashCode(), builderMatch.hashCode());
        assertNotEquals(allArgsMatch.getMatchId(),builderMatch.getMatchId());
    }

}
