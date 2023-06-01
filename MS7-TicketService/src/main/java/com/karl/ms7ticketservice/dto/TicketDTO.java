package com.karl.ms7ticketservice.dto;

import com.karl.ms7ticketservice.dto.match.MatchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Integer ticketId;
    private String customerNane;
    private Float price;

    // TODO: 5/12/2023 : JOIN match
    private MatchDTO match;
}
