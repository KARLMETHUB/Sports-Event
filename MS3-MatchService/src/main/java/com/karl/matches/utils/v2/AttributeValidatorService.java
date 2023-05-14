package com.karl.matches.utils.v2;

import com.karl.matches.dto.v2.MatchDTO;
import com.karl.matches.exception.custom.MatchCreateException;
import com.karl.matches.exception.custom.ResourceIdShouldBeNullException;
import org.springframework.stereotype.Service;

import static com.karl.matches.constants.ExceptionMessages.*;

@Service
public class AttributeValidatorService {

    private final RestClientService restClientService;

    public AttributeValidatorService(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    public void verifyRequiredValues(MatchDTO matchDTO)
            throws MatchCreateException {

        if (matchDTO.getFieldDTO() == null || matchDTO.getFieldDTO().getFieldId() == null)
            throw new MatchCreateException(FIELD_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityFieldExists(matchDTO.getFieldDTO().getFieldId())))
            throw new MatchCreateException(FIELD_ID_NON_EXISTENT);

        if (matchDTO.getTournamentDTO() == null || matchDTO.getTournamentDTO().getTournamentId() == null)
            throw new MatchCreateException(TOURNAMENT_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityTournamentExists(matchDTO.getTournamentDTO().getTournamentId())))
            throw new MatchCreateException(TOURNAMENT_ID_NON_EXISTENT);

        if (matchDTO.getHomeTeamDto() == null || matchDTO.getHomeTeamDto().getTeamId() == null)
            throw new MatchCreateException(HOME_TEAM_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityTeamExists(matchDTO.getHomeTeamDto().getTeamId())))
            throw new MatchCreateException(HOME_TEAM_NON_EXISTENT);

        if (matchDTO.getAwayTeamDto() == null || matchDTO.getAwayTeamDto().getTeamId() == null)
            throw new MatchCreateException(AWAY_TEAM_ID_REQUIRED);

        if (Boolean.FALSE.equals(restClientService.verityTeamExists(matchDTO.getAwayTeamDto().getTeamId())))
            throw new MatchCreateException(AWAY_TEAM_NON_EXISTENT);

    }
}
