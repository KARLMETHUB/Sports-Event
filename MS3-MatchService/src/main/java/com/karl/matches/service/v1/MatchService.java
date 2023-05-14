//package com.karl.matches.service.v1;
//
//import com.karl.matches.clients.TeamClient;
//import com.karl.matches.dto.v1.MatchDTO;
//import com.karl.matches.dto.v1.TeamDTO;
//import com.karl.matches.entity.v1.Match;
//import com.karl.matches.exception.custom.DtoToEntityMappingException;
//import com.karl.matches.exception.custom.ResourceExistsException;
//import com.karl.matches.exception.custom.ResourceNotFoundException;
//import com.karl.matches.repo.v1.MatchRepository;
//import com.karl.matches.utils.v1.DTOMatchMapper;
//import com.karl.matches.utils.v1.MatchDTOMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static com.karl.matches.constants.ExceptionMessages.*;
//
//@Deprecated
//@Service
//public class MatchService {
//
//    private final MatchRepository matchRepository;
//    private final MatchDTOMapper matchDTOMapper;
//    private final DTOMatchMapper dtoMatchMapper;
//    private final TeamClient teamClient;
//
//    public MatchService(MatchRepository matchRepository, MatchDTOMapper matchDTOMapper, DTOMatchMapper dtoMatchMapper, TeamClient teamClient) {
//        this.matchRepository = matchRepository;
//        this.matchDTOMapper = matchDTOMapper;
//        this.dtoMatchMapper = dtoMatchMapper;
//        this.teamClient = teamClient;
//    }
//
//    public List<MatchDTO> getAllMatches(){
//        return matchRepository
//                .findAll()
//                .stream()
//                .map(matchDTOMapper)
//                .collect(Collectors.toList());
//    }
//
//    public MatchDTO getMatch(int matchId) throws ResourceNotFoundException {
//
//        MatchDTO matchDTO = matchRepository
//                .findOneByMatchId(matchId)
//                .map(matchDTOMapper)
//                .orElseThrow(()
//                        -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
//
//        List<TeamDTO> teams = Arrays.asList(teamClient.getTeamsMatches(matchDTO.getMatchId()));
//        matchDTO.setObjectList(teams);
//        return matchDTO;
//    }
//
//    public MatchDTO createMatch(MatchDTO matchDTO) throws ResourceExistsException, DtoToEntityMappingException {
//        try {
//            getMatch(matchDTO.getMatchId());
//            throw new ResourceExistsException(MATCH_EXISTS_EXCEPTION_MESSAGE);
//        } catch (ResourceNotFoundException e) {
//
//            Match matchEntity = Optional
//                    .of(matchDTO)
//                    .map(dtoMatchMapper)
//                    .orElseThrow(() ->
//                            new DtoToEntityMappingException(ENTITY_MAPPING_EXCEPTION_MESSAGE));
//
//            /*Does not create Attributes when added*/
//            matchEntity = matchRepository.save(matchEntity);
//
//            return Optional.of(matchEntity)
//                    .map(matchDTOMapper)
//                    .orElseThrow(() ->
//                            new DtoToEntityMappingException(ENTITY_MAPPING_EXCEPTION_MESSAGE));
//        }
//    }
//
//}
