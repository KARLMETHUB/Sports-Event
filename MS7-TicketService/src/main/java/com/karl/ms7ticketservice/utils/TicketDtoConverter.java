package com.karl.ms7ticketservice.utils;

import com.karl.ms7ticketservice.dto.TicketDTO;
import com.karl.ms7ticketservice.entity.Ticket;

public class TicketDtoConverter {

    private TicketDtoConverter() {}

    public static Ticket toEntity(TicketDTO t) {
        return Ticket.builder()
                .ticketId(t.getTicketId())
                .price(t.getPrice())
                .customerNane(t.getCustomerNane())
                .build();
    }

    public static TicketDTO toDto(Ticket t) {
        return TicketDTO.builder()
                .ticketId(t.getTicketId())
                .price(t.getPrice())
                .customerNane(t.getCustomerNane())
                .build();
    }

}
