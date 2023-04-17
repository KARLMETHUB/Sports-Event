package com.karl.matches.service;

import com.karl.matches.exception.custom.ResourceNotFoundException;
import com.karl.matches.model.dto.MatchDTO;
import com.karl.matches.model.dtomapper.MatchDTOMapper;
import com.karl.matches.repo.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchDTOMapper matchDTOMapper;

    public MatchService(MatchRepository matchRepository, MatchDTOMapper matchDTOMapper) {
        this.matchRepository = matchRepository;
        this.matchDTOMapper = matchDTOMapper;
    }

    public List<MatchDTO> getAllMatches(){
        return matchRepository
                .findAll()
                .stream()
                .map(matchDTOMapper)
                .collect(Collectors.toList());
    }

    public MatchDTO getMatch(int matchId) throws ResourceNotFoundException {
        return matchRepository
                .findOneByMatchId(matchId)
                .map(matchDTOMapper)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Resource not found"));
    }
}
