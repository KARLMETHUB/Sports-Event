package com.karl.ms7ticketservice.service;

import com.karl.ms7ticketservice.client.MatchClient;
import com.karl.ms7ticketservice.dto.TicketDTO;
import com.karl.ms7ticketservice.dto.match.MatchDTO;
import com.karl.ms7ticketservice.entity.Ticket;
import com.karl.ms7ticketservice.exception.FeignClientUnavailableException;
import com.karl.ms7ticketservice.exception.ResourceNotFoundException;
import com.karl.ms7ticketservice.utils.TicketDtoConverter;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.karl.ms7ticketservice.constants.ExceptionMessage.*;

@Service
public class RestClientService {
    private final MatchClient client;
    private static final Logger logger = LogManager.getLogger(RestClientService.class);

    public RestClientService(MatchClient client) {
        this.client = client;
    }

    public Ticket retrieveAttributeData(Ticket t)
            throws FeignClientUnavailableException, ResourceNotFoundException {
        try {
            MatchDTO match = client.getMatch(t.getMatchId());
            t.setMatchDTO(match);
            return t;
        } catch (FeignException.ServiceUnavailable e) {
            logger.info("Bad request exception occurred: {}", e.getMessage());
        } catch (FeignException e) {
            logger.info("Feign exception occurred: {}", e.getMessage());
            if (HttpStatus.NOT_FOUND.value() == e.status())
                throw new ResourceNotFoundException(
                        String.format(RESOURCE_MATCH_NOT_FOUND,t.getMatchId()));
        } catch (Exception e) {
            logger.info("An exception occurred: {}", e.getMessage());
        }

        throw new FeignClientUnavailableException(SERVICE_UNAVAILABLE);
    }

    public List<Ticket> retrieveAttributeData(List<Ticket> tickets)
            throws FeignClientUnavailableException {
        try {
            List<MatchDTO> match = client.getMatches();

            for (Ticket t: tickets) {
                Optional<MatchDTO> matchDTO = match
                        .parallelStream()
                        .filter(m -> Objects.equals(m.getMatchId(), t.getMatchId()))
                        .findFirst();

                if (matchDTO.isEmpty())
                    break;

                t.setMatchDTO(matchDTO.get());
            }
            return tickets;
        } catch (FeignException.ServiceUnavailable e) {
            logger.info("Bad request exception occurred: {}", e.getMessage());
        } catch (FeignException e) {
            logger.info("Feign exception occurred: {}", e.getMessage());
        } catch (Exception e) {
            logger.info("An exception occurred: {}", e.getMessage());
        }

        throw new FeignClientUnavailableException(SERVICE_UNAVAILABLE);
    }
}
