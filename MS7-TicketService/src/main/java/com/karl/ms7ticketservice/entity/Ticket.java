package com.karl.ms7ticketservice.entity;

import com.karl.ms7ticketservice.dto.match.MatchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mt_tickets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @Column(name = "ticket_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;

    @Column(name = "customer_nane",nullable = false)
    private String customerNane;

    @Column(name = "ticket_price",nullable = false)
    private Float price;

    @Column(name = "match_id",nullable = false)
    private Integer matchId;

    @Transient
    private MatchDTO matchDTO;
}
