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
//public class DTOMatchMapper implements Function<MatchDTO, Match> {
//    @Override
//    public Match apply(MatchDTO matchDTO) {
//        return new Match(
//                matchDTO.getMatchId(),
//                matchDTO.getField(),
//                matchDTO.getTournament(),
//                matchDTO.getParticipantsId(),
//                matchDTO.getDateTime(),
//                matchDTO.getTickets(),
//                matchDTO.getTeams());
//    }
//
//    @Override
//    public <V> Function<V, Match> compose(Function<? super V, ? extends MatchDTO> before) {
//        return Function.super.compose(before);
//    }
//
//    @Override
//    public <V> Function<MatchDTO, V> andThen(Function<? super Match, ? extends V> after) {
//        return Function.super.andThen(after);
//    }
//}
