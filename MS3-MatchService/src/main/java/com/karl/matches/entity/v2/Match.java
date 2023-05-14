package com.karl.matches.entity.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mt_matches")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @Column(name = "match_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;

    @Column(name = "field_id",nullable = false)
    private Integer fieldId;

    @Column(name = "tournament_id",nullable = false)
    private Integer tournamentId;

    @Column(name = "date_time",nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "home_team_id",nullable = false)
    private Integer homeTeamId;

    @Column(name = "away_team_id",nullable = false)
    private Integer awayTeamId;

}
