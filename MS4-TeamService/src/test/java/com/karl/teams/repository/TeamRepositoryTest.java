package com.karl.teams.repository;

import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.model.entities.Team;
import com.karl.teams.utils.TeamDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TeamRepositoryTest {

    @Autowired
    private TeamsRepository teamsRepository;

    private List<TeamDTO> teamDTOs;
    private List<Team> teams;

    @BeforeEach
    public void init() {

        teamDTOs = new ArrayList<>();
        teamDTOs.add(new TeamDTO(1,"Test", Collections.emptySet()));
        teamDTOs.add(new TeamDTO(2,"Test2", Collections.emptySet()));
        teamDTOs.add(new TeamDTO(3,"Test3", Collections.emptySet()));

        teams = teamDTOs
                .stream()
                .map(TeamDtoConverter::toEntity)
                .collect(Collectors.toList());
    }
    @Test
    void saveReturnsSavedValues() {

        Team team = teamsRepository.save(teams.get(0));

        assertThat(team).isNotNull();
        assertThat(team.getTeamId()).isNotNull();
        assertThat(team.getTeamId()).isPositive();
    }


}
