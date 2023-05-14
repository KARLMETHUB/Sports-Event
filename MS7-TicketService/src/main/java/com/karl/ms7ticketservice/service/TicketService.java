package com.karl.ms7ticketservice.service;

import com.karl.ms7ticketservice.dto.TicketDTO;
import com.karl.ms7ticketservice.entity.Ticket;
import com.karl.ms7ticketservice.exception.ResourceNotFoundException;
import com.karl.ms7ticketservice.exception.TicketCreateException;
import com.karl.ms7ticketservice.exception.TicketUpdateException;
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

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicket(int ticketId) throws ResourceNotFoundException {
        Optional<Ticket> field = ticketRepository.findById(ticketId);

        return field.orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,ticketId)));

    }
    public Ticket createTicket(TicketDTO ticketDTO) throws TicketCreateException {

        if (ticketDTO.getTicketId() != null)
            throw new TicketCreateException(ID_SHOULD_BE_NULL_ON_CREATE);

        return ticketRepository.save(toEntity(ticketDTO));
    }

    public Ticket updateTicket(TicketDTO ticketDTO) throws TicketUpdateException, ResourceNotFoundException {

        if (ticketDTO.getTicketId() == null)
            throw new TicketUpdateException(ID_REQUIRED);

        if (!ticketRepository.existsById(ticketDTO.getTicketId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,ticketDTO.getTicketId()));

        return ticketRepository.save(toEntity(ticketDTO));
    }

    public String deleteTicket(int ticketId) throws ResourceNotFoundException {

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
