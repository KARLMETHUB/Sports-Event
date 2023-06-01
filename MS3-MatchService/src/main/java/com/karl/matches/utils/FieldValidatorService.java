package com.karl.matches.utils;


import com.karl.matches.dto.MatchDTO;
import com.karl.matches.exception.MissingOrNonExistentFieldException;
import com.karl.matches.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import static com.karl.matches.constants.ExceptionMessages.*;

@Service
public class FieldValidatorService {

    private final RestClientService restClientService;

    public FieldValidatorService(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    public void verifyRequiredValues(MatchDTO matchDTO)
            throws MissingOrNonExistentFieldException {

        if (matchDTO.getFieldDTO() == null || matchDTO.getFieldDTO().getFieldId() == null)
            throw new MissingOrNonExistentFieldException(FIELD_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityFieldExists(matchDTO.getFieldDTO().getFieldId())))
            throw new MissingOrNonExistentFieldException(FIELD_ID_NON_EXISTENT);

        if (matchDTO.getTournamentDTO() == null || matchDTO.getTournamentDTO().getTournamentId() == null)
            throw new MissingOrNonExistentFieldException(TOURNAMENT_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityTournamentExists(matchDTO.getTournamentDTO().getTournamentId())))
            throw new MissingOrNonExistentFieldException(TOURNAMENT_ID_NON_EXISTENT);

        if (matchDTO.getHomeTeamDto() == null || matchDTO.getHomeTeamDto().getTeamId() == null)
            throw new MissingOrNonExistentFieldException(HOME_TEAM_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityTeamExists(matchDTO.getHomeTeamDto().getTeamId())))
            throw new MissingOrNonExistentFieldException(HOME_TEAM_NON_EXISTENT);

        if (matchDTO.getAwayTeamDto() == null || matchDTO.getAwayTeamDto().getTeamId() == null)
            throw new MissingOrNonExistentFieldException(AWAY_TEAM_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityTeamExists(matchDTO.getAwayTeamDto().getTeamId())))
            throw new MissingOrNonExistentFieldException(AWAY_TEAM_NON_EXISTENT);

    }
}
