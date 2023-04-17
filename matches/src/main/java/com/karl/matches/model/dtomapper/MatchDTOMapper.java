package com.karl.matches.model.dtomapper;

import com.karl.matches.model.dto.MatchDTO;
import com.karl.matches.model.entities.Match;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MatchDTOMapper implements Function<Match, MatchDTO> {

    @Override
    public MatchDTO apply(Match match) {
        return new MatchDTO(
                match.getMatchId(),
                match.getField(),
                match.getTournament(),
                match.getParticipantsId(),
                match.getDateTime(),
                match.getTickets(),
                match.getTeams());
    }

    @Override
    public <V> Function<V, MatchDTO> compose(Function<? super V, ? extends Match> before) {
        return Function.super.compose(before);
    }

    @Override
    public <V> Function<Match, V> andThen(Function<? super MatchDTO, ? extends V> after) {
        return Function.super.andThen(after);
    }
}

