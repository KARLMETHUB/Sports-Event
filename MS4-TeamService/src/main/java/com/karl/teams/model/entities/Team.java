package com.karl.teams.model.entities;

import com.karl.teams.model.dto.PlayerDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mt_teams")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @Column(name = "team_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    @Column(name = "team_name",nullable = false)
    private String teamName;

    @Transient
    private Set<PlayerDTO> players;


}
