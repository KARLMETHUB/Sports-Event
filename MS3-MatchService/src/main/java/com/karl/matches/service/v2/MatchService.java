package com.karl.matches.service.v2;


import com.karl.matches.dto.v2.MatchDTO;
import com.karl.matches.entity.v2.Match;
import com.karl.matches.exception.custom.MatchCreateException;
import com.karl.matches.exception.custom.ResourceIdRequiredException;
import com.karl.matches.exception.custom.ResourceIdShouldBeNullException;
import com.karl.matches.exception.custom.ResourceNotFoundException;
import com.karl.matches.repo.v2.MatchRepository;
import com.karl.matches.utils.v2.AttributeValidatorService;
import com.karl.matches.utils.v2.RestClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.karl.matches.constants.ExceptionMessages.*;
import static com.karl.matches.constants.ResponseMessage.*;
import static com.karl.matches.utils.v2.MatchDtoConverter.toEntity;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final RestClientService restClientService;
    private final AttributeValidatorService attributeValidatorService;

    public MatchService(MatchRepository matchRepository,
                        RestClientService restClientService,
                        AttributeValidatorService attributeValidatorService) {
        this.matchRepository = matchRepository;
        this.restClientService = restClientService;
        this.attributeValidatorService = attributeValidatorService;
    }

    public List<MatchDTO> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        List<MatchDTO> matchDTOS = new ArrayList<>();

        for (Match m : matches) {
            matchDTOS.add(restClientService.retrieveAttributeData(m));
        }

        return matchDTOS;
    }

    public MatchDTO getByMatchId(int matchId) throws ResourceNotFoundException {
        Optional<Match> match = matchRepository.findById(matchId);

        if (match.isEmpty())
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,matchId));

        return restClientService.retrieveAttributeData(match.get());
    }

    public MatchDTO createMatch(MatchDTO matchDTO)
            throws ResourceIdShouldBeNullException, MatchCreateException {

        if (matchDTO.getMatchId() != null)
            throw new ResourceIdShouldBeNullException(ID_SHOULD_BE_NULL_ON_CREATE);

        attributeValidatorService.verifyRequiredValues(matchDTO);

        return restClientService.retrieveAttributeData(matchRepository.save(toEntity(matchDTO)));
    }

    public MatchDTO updateMatch(MatchDTO matchDTO)
            throws ResourceIdRequiredException,
            ResourceNotFoundException,
            MatchCreateException {

        if (matchDTO.getMatchId() == null)
            throw new ResourceIdRequiredException(ID_REQUIRED);

        if (!matchRepository.existsById(matchDTO.getMatchId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,matchDTO.getMatchId()));

        attributeValidatorService.verifyRequiredValues(matchDTO);

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
