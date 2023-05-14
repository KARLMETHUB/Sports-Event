//package com.karl.matches.utils.v1;
//
//import com.karl.matches.dto.v1.MatchDTO;
//import com.karl.matches.entity.v1.Match;
//import org.springframework.stereotype.Service;
//
//import java.util.function.Function;
//
//@Deprecated
//@Service
//public class MatchDTOMapper implements Function<Match, MatchDTO> {
//
//    @Override
//    public MatchDTO apply(Match match) {
//        return new MatchDTO(
//                match.getMatchId(),
//                match.getField(),
//                match.getTournament(),
//                match.getParticipantsId(),
//                match.getDateTime(),
//                match.getTickets(),
//                match.getTeams(),
//                null
//        );
//    }
//
//    @Override
//    public <V> Function<V, MatchDTO> compose(Function<? super V, ? extends Match> before) {
//        return Function.super.compose(before);
//    }
//
//    @Override
//    public <V> Function<Match, V> andThen(Function<? super MatchDTO, ? extends V> after) {
//        return Function.super.andThen(after);
//    }
//}
//
