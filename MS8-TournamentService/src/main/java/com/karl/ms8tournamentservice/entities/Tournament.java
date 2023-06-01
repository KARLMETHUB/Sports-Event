package com.karl.ms8tournamentservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "mt_tournament")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {

    @Id
    @Column(name = "tournament_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tournamentId;
    @Column(name = "tournament_name",nullable = false)
    private String tournamentName;
    @Column(name = "sports_category",nullable = false)
    private String sportsCategory;
    @Column(name = "tournament_style",nullable = false)
    private String tournamentStyle;

}
