package com.karl.ms7ticketservice.service;

import com.google.common.base.Strings;
import com.karl.ms7ticketservice.dto.TicketDTO;
import com.karl.ms7ticketservice.entity.Ticket;
import com.karl.ms7ticketservice.exception.*;
import com.karl.ms7ticketservice.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.karl.ms7ticketservice.constants.ExceptionMessage.*;
import static com.karl.ms7ticketservice.constants.ResponseMessage.*;
import static com.karl.ms7ticketservice.utils.TicketDtoConverter.toEntity;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final RestClientService restClientService;
    private final FieldValidationService fieldValidationService;

    public TicketService(TicketRepository ticketRepository,
                         RestClientService restClientService, FieldValidationService fieldValidationService) {
        this.ticketRepository = ticketRepository;
        this.restClientService = restClientService;
        this.fieldValidationService = fieldValidationService;
    }

    public List<Ticket> getAllTickets()
            throws FeignClientUnavailableException {

        return restClientService.retrieveAttributeData(ticketRepository.findAll());
    }

    public Ticket getTicket(int ticketId)
            throws ResourceNotFoundException,
            FeignClientUnavailableException {
        Optional<Ticket> field = ticketRepository.findById(ticketId);

        if (field.isEmpty())
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,ticketId));
        return restClientService
                .retrieveAttributeData(field.get());
    }
    public Ticket createTicket(TicketDTO ticketDTO)
            throws TicketCreateException,
            MissingOrNonExistentFieldException,
            FeignClientUnavailableException,
            ResourceNotFoundException {

        if (ticketDTO.getTicketId() != null)
            throw new TicketCreateException(ID_SHOULD_BE_NULL_ON_CREATE);

        fieldValidationService.validateRequiredFields(ticketDTO);

        Ticket saveResult = ticketRepository.save(toEntity(ticketDTO));
        return restClientService.retrieveAttributeData(saveResult);
    }

    public Ticket updateTicket(TicketDTO ticketDTO)
            throws TicketUpdateException,
            ResourceNotFoundException,
            MissingOrNonExistentFieldException,
            FeignClientUnavailableException {

        if (ticketDTO.getTicketId() == null)
            throw new TicketUpdateException(ID_REQUIRED);

        if (!ticketRepository.existsById(ticketDTO.getTicketId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,ticketDTO.getTicketId()));

        fieldValidationService.validateRequiredFields(ticketDTO);

        Ticket saveResult = ticketRepository.save(toEntity(ticketDTO));
        return restClientService.retrieveAttributeData(saveResult);
    }

    public String deleteTicket(int ticketId)
            throws ResourceNotFoundException {

        if (ticketId < 1)
            throw new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO);

        if(!ticketRepository.existsById(ticketId))
            throw new ResourceNotFoundException(
                    String.format(RESOURCE_NOT_FOUND,ticketId));

        ticketRepository.deleteById(ticketId);

        return String.format(ticketRepository.existsById(ticketId) ?
                DELETE_MESSAGE_FAILED : DELETE_MESSAGE_SUCCESS, ticketId);
    }

}
