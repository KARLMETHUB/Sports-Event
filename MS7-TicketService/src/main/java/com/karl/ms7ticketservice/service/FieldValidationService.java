package com.karl.ms7ticketservice.service;

import com.google.common.base.Strings;
import com.karl.ms7ticketservice.client.MatchClient;
import com.karl.ms7ticketservice.dto.TicketDTO;
import com.karl.ms7ticketservice.exception.MissingOrNonExistentFieldException;
import com.karl.ms7ticketservice.exception.TicketUpdateException;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.karl.ms7ticketservice.constants.ExceptionMessage.*;

@Service
public class FieldValidationService {

    private final MatchClient client;
    private static final Logger logger = LogManager.getLogger(RestClientService.class);

    public FieldValidationService(MatchClient client) {
        this.client = client;
    }

    public void validateRequiredFields(TicketDTO ticketDTO)
            throws MissingOrNonExistentFieldException {

        if (Strings.isNullOrEmpty(ticketDTO.getCustomerNane()))
            throw new MissingOrNonExistentFieldException(CUSTOMER_NAME_REQUIRED);

        if (ticketDTO.getPrice() == null)
            throw new MissingOrNonExistentFieldException(PRICE_REQUIRED);

        if (ticketDTO.getPrice() < 0)
            throw new MissingOrNonExistentFieldException(PRICE_NEGATIVE);

        if (ticketDTO.getMatch() == null)
            throw new MissingOrNonExistentFieldException(MATCH_REQUIRED);

        if (ticketDTO.getMatch().getMatchId() == null)
            throw new MissingOrNonExistentFieldException(MATCH_REQUIRED);

        if (!verityMatchExists(ticketDTO.getMatch().getMatchId()))
            throw new MissingOrNonExistentFieldException(MATCH_REQUIRED);

    }

    public boolean verityMatchExists(int id) {
        boolean b = false;

        try {
            b = client.getMatch(id) != null;
        } catch (FeignException.BadRequest e) {
            logger.info(BAD_REQUEST_EXCEPTION, e.getMessage());
        } catch (FeignException e) {
            logger.info(FEIGN_EXCEPTION, e.getMessage());
        } catch (Exception e) {
            logger.info(GENERIC_EXCEPTION, e.getMessage());
        }
        return b;
    }
}
