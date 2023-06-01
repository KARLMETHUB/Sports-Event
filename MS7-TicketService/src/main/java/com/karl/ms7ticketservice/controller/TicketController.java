package com.karl.ms7ticketservice.controller;

import com.karl.ms7ticketservice.dto.TicketDTO;
import com.karl.ms7ticketservice.exception.*;
import com.karl.ms7ticketservice.service.TicketService;
import com.karl.ms7ticketservice.utils.TicketDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.karl.ms7ticketservice.utils.TicketDtoConverter.toDto;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TicketDTO>> getAllTickets()
            throws FeignClientUnavailableException {
        return new ResponseEntity<>(
                ticketService
                        .getAllTickets()
                        .stream().map(TicketDtoConverter::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getField(@PathVariable("ticketId") int ticketId)
            throws ResourceNotFoundException,
            FeignClientUnavailableException {

        return new ResponseEntity<>(
                toDto(ticketService.getTicket(ticketId))
                , HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TicketDTO> createTicket (@RequestBody TicketDTO ticketDTO)
            throws TicketCreateException,
            MissingOrNonExistentFieldException,
            FeignClientUnavailableException,
            ResourceNotFoundException {
        return new ResponseEntity<>(
                toDto(ticketService.createTicket(ticketDTO)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update")
    public ResponseEntity<TicketDTO> updateField(@RequestBody TicketDTO ticketDTO)
            throws TicketUpdateException,
            ResourceNotFoundException,
            MissingOrNonExistentFieldException,
            FeignClientUnavailableException {

        return new ResponseEntity<>(
                toDto(ticketService.updateTicket(ticketDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<String> deleteFieldById(@PathVariable("ticketId") int ticketId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                ticketService.deleteTicket(ticketId),
                HttpStatus.OK);
    }
}
