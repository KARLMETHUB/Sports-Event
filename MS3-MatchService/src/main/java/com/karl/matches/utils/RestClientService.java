package com.karl.matches.utils;

import com.karl.matches.clients.FieldClient;
import com.karl.matches.clients.TeamClient;
import com.karl.matches.clients.TournamentClient;
import com.karl.matches.dto.FieldDTO;
import com.karl.matches.dto.MatchDTO;
import com.karl.matches.dto.TeamDTO;
import com.karl.matches.dto.TournamentDTO;
import com.karl.matches.entity.Match;
import com.karl.matches.exception.FeignClientUnavailableException;
import com.karl.matches.exception.MissingOrNonExistentFieldException;
import com.karl.matches.exception.ResourceNotFoundException;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.karl.matches.constants.ExceptionMessages.*;

@Service
public class RestClientService {

    private final TeamClient teamClient;
    private final TournamentClient tournamentClient;
    private final FieldClient fieldClient;

    private static final Logger logger = LogManager.getLogger(RestClientService.class);



    public RestClientService(TeamClient teamClient, TournamentClient tournamentClient, FieldClient fieldClient) {
        this.teamClient = teamClient;
        this.tournamentClient = tournamentClient;
        this.fieldClient = fieldClient;
    }

    public MatchDTO retrieveAttributeData(Match m) throws FeignClientUnavailableException {
        try {
            FieldDTO field = fieldClient.getField(m.getFieldId());
            TournamentDTO tournament = tournamentClient.getTournament(m.getTournamentId());

            TeamDTO home = teamClient.getTeam(m.getHomeTeamId());
            TeamDTO away = teamClient.getTeam(m.getAwayTeamId());

            return MatchDtoConverter.toDto(m,tournament,field,home,away);
        } catch (FeignException.ServiceUnavailable e) {
            logger.info("Bad request exception occurred: {}", e.getMessage());
        } catch (FeignException e) {
            logger.info("Feign exception occurred: {}", e.getMessage());
        } catch (Exception e) {
            logger.info("An exception occurred: {}", e.getMessage());
        }

        throw new FeignClientUnavailableException(SERVICE_UNAVAILABLE);
    }

    public boolean verityFieldExists(int id) throws MissingOrNonExistentFieldException {
        boolean b = false;

        try {
            b = fieldClient.getField(id) != null;
        } catch (FeignException.BadRequest e) {
            logger.info(BAD_REQUEST_EXCEPTION, e.getMessage());
        } catch (FeignException e) {
            logger.info(FEIGN_EXCEPTION, e.getMessage());
            if (HttpStatus.NOT_FOUND.value() == e.status())
                throw new MissingOrNonExistentFieldException(
                        String.format(RESOURCE_FIELD_NOT_FOUND,id));
        } catch (Exception e) {
            logger.info(GENERIC_EXCEPTION, e.getMessage());
        }
        return b;
    }

    public boolean verityTournamentExists(int id) throws MissingOrNonExistentFieldException {
        boolean b = false;

        try {
            b = tournamentClient.getTournament(id) != null;
        } catch (FeignException.BadRequest e) {
            logger.info(BAD_REQUEST_EXCEPTION, e.getMessage());
        } catch (FeignException e) {
            logger.info(FEIGN_EXCEPTION, e.getMessage());
            if (HttpStatus.NOT_FOUND.value() == e.status())
                throw new MissingOrNonExistentFieldException(
                        String.format(RESOURCE_TOURNAMENT_NOT_FOUND,id));
        } catch (Exception e) {
            logger.info(GENERIC_EXCEPTION, e.getMessage());
        }
        return b;
    }

    public boolean verityTeamExists(int id) throws MissingOrNonExistentFieldException {
        boolean b = false;

        try {
            b = teamClient.getTeam(id) != null;
        } catch (FeignException.BadRequest e) {
            logger.info(BAD_REQUEST_EXCEPTION, e.getMessage());
        } catch (FeignException e) {
            logger.info(FEIGN_EXCEPTION, e.getMessage());
            if (HttpStatus.NOT_FOUND.value() == e.status())
                throw new MissingOrNonExistentFieldException(
                        String.format(RESOURCE_TEAM_NOT_FOUND,id));
        } catch (Exception e) {
            logger.info(GENERIC_EXCEPTION, e.getMessage());
        }
        return b;
    }
}
