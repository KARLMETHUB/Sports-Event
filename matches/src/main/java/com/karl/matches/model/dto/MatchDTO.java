package com.karl.matches.model.dto;

import com.karl.matches.model.entities.Field;
import com.karl.matches.model.entities.Team;
import com.karl.matches.model.entities.Ticket;
import com.karl.matches.model.entities.Tournament;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public record MatchDTO (
        Integer matchId,
        Field field,
        Tournament tournament,
        String participantsId,
        LocalDateTime dateTime,
        Set<Ticket> tickets,
        Collection<Team> teams
) { }