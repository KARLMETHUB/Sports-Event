package com.karl.matches.service;


import com.karl.matches.dto.MatchDTO;
import com.karl.matches.entity.Match;
import com.karl.matches.exception.*;
import com.karl.matches.repo.MatchRepository;
import com.karl.matches.utils.FieldValidatorService;
import com.karl.matches.utils.RestClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.karl.matches.constants.ExceptionMessages.*;
import static com.karl.matches.constants.ResponseMessage.*;
import static com.karl.matches.utils.MatchDtoConverter.toEntity;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final RestClientService restClientService;
    private final FieldValidatorService fieldValidatorService;

    public MatchService(MatchRepository matchRepository,
                        RestClientService restClientService,
                        FieldValidatorService fieldValidatorService) {
        this.matchRepository = matchRepository;
        this.restClientService = restClientService;
        this.fieldValidatorService = fieldValidatorService;
    }

    public List<MatchDTO> getAllMatches() throws FeignClientUnavailableException {
        List<Match> matches = matchRepository.findAll();
        List<MatchDTO> matchDTOS = new ArrayList<>();

        for (Match m : matches) {
            matchDTOS.add(restClientService.retrieveAttributeData(m));
        }

        return matchDTOS;
    }

    public MatchDTO getByMatchId(int matchId)
            throws ResourceNotFoundException, FeignClientUnavailableException {
        Optional<Match> match = matchRepository.findById(matchId);

        if (match.isEmpty())
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,matchId));

        return restClientService.retrieveAttributeData(match.get());
    }

    public MatchDTO createMatch(MatchDTO matchDTO)
            throws ResourceIdShouldBeNullException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {

        if (matchDTO.getMatchId() != null)
            throw new ResourceIdShouldBeNullException(ID_SHOULD_BE_NULL_ON_CREATE);

        fieldValidatorService.verifyRequiredValues(matchDTO);

        return restClientService.retrieveAttributeData(matchRepository.save(toEntity(matchDTO)));
    }

    public MatchDTO updateMatch(MatchDTO matchDTO)
            throws ResourceIdRequiredException,
            ResourceNotFoundException,
            MatchCreateException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {

        if (matchDTO.getMatchId() == null)
            throw new ResourceIdRequiredException(ID_REQUIRED);

        if (!matchRepository.existsById(matchDTO.getMatchId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,matchDTO.getMatchId()));

        fieldValidatorService.verifyRequiredValues(matchDTO);

        return restClientService.retrieveAttributeData(matchRepository.save(toEntity(matchDTO)));
    }

    public String deleteMatch(int matchId) throws ResourceNotFoundException {

        if (matchId < 1)
            throw new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO);

        if(!matchRepository.existsById(matchId))
            throw new ResourceNotFoundException(
                    String.format(RESOURCE_NOT_FOUND,matchId));

        matchRepository.deleteById(matchId);

        return String.format(matchRepository.existsById(matchId) ?
                DELETE_MESSAGE_FAILED : DELETE_MESSAGE_SUCCESS, matchId);
    }

}
