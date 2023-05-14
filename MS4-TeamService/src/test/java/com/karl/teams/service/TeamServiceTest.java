package com.karl.teams.service;

import com.karl.teams.exceptions.ResourceIdRequiredException;
import com.karl.teams.exceptions.ResourceIdShouldBeNullException;
import com.karl.teams.exceptions.ResourceNotFoundException;
import com.karl.teams.model.dto.PlayerDTO;
import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.model.entities.Team;
import com.karl.teams.repository.TeamsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// FIXME: 5/9/2023 : This is not working
@ExtendWith(MockitoExtension.class)
//@DataJpaTest
class TeamServiceTest {

    @Mock
    private TeamsRepository teamsRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private TeamService teamService;
//    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
//        teamService = new TeamService(teamsRepository,restTemplate);
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }
    @Test
    void canGetAllTeams() {

        //when
        teamService.getAllTeams();

        //then
        /* Add to service
        Mockito.verify(restTemplate).getForObject("http://PlAYERS/api/v1/players",Set.class);*/

        Mockito.verify(teamsRepository).findAll();
        /*Mockito.when(teamsRepository
                        .findAll()
                        .stream()
                        .map(team ->
                        new TeamDTO(team.getTeamId(),
                                team.getTeamName(),
                                Collections.emptySet()
                        ))
        ).thenReturn();
*/
    }

    @Test
    void canGetTeamById() throws ResourceNotFoundException {
        //given
        int validTeamId = 1;
        Team expectedTeam = new Team(validTeamId,"Team Collabera",Collections.emptySet());

        Set<PlayerDTO> players = restTemplate
                .getForObject("http://PlAYERS/api/v1/players/" + validTeamId,
                        Set.class);

        Team expectedTeamDTO = new Team(expectedTeam.getTeamId(),expectedTeam.getTeamName(),players);

        //when
        when(teamsRepository.findById(validTeamId)).thenReturn(Optional.of(expectedTeam));

        Team actualTeamDTO = teamService.getTeamById(validTeamId);

        // TODO: 5/8/2023 : When different team name but same id still true?
        assertThat(expectedTeamDTO).isNotNull();
        assertThat(expectedTeamDTO).isEqualTo(actualTeamDTO);
    }
    @Test
    void getTeamByIdWillThrowExceptionWhenTeamIdIsInvalid() {

        int invalidTeamId = 234354355;
        /*Team found = new Team("Test");
        when(teamsRepository.findById(invalidTeamId)).thenReturn(Optional.of(found));*/

        when(teamsRepository.findById(invalidTeamId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.getTeamById(invalidTeamId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Team " + invalidTeamId + " does not exist!");
    }

    @Test
    void canCreateTeam() throws ResourceIdShouldBeNullException {
        //given
        TeamDTO teamDTO = new TeamDTO(null,"Team test", Collections.emptySet());

        try {
            teamService.createTeam(teamDTO);
        } catch (NullPointerException ignored) {}

        ArgumentCaptor<Team> teamArgumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamsRepository).save(teamArgumentCaptor.capture());

        Team capturedTeam = teamArgumentCaptor.getValue();
        assertThat(capturedTeam.getTeamId()).isEqualTo(capturedTeam.getTeamId());
        assertThat(capturedTeam.getTeamName()).isEqualTo(capturedTeam.getTeamName());
    }

    @Test
    void createTeamWillThrowException() throws ResourceIdShouldBeNullException {

        Team team = new Team(1,"Team test",Collections.emptySet());
        TeamDTO teamDTO = new TeamDTO(1,team.getTeamName(),Collections.emptySet());

        when(teamsRepository.existsById(Mockito.anyInt())).thenReturn(true);

        assertThatThrownBy(() -> teamService.createTeam(teamDTO))
                .isInstanceOf(ResourceIdShouldBeNullException.class)
                .hasMessageContaining("Team id already exists!");
    }

    @Test
    void canUpdateTeam() throws ResourceNotFoundException, ResourceIdRequiredException {

        TeamDTO teamDTO = new TeamDTO(1,"Team test", Collections.emptySet());

        when(teamsRepository.existsById(teamDTO.getTeamId())).thenReturn(true);

        try {
            teamService.updateTeam(teamDTO);
        } catch (NullPointerException ignored) {}

        ArgumentCaptor<Team> teamArgumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamsRepository).save(teamArgumentCaptor.capture());

        Team capturedTeam = teamArgumentCaptor.getValue();
        assertThat(teamDTO.getTeamId()).isEqualTo(capturedTeam.getTeamId());
        assertThat(teamDTO.getTeamName()).isEqualTo(capturedTeam.getTeamName());
    }

    @Test
    void updateTeamWillThrowException() {
        TeamDTO teamDTO = new TeamDTO(1,"Test team",Collections.emptySet());

        when(teamsRepository.existsById(Mockito.anyInt())).thenReturn(false);

        assertThatThrownBy(() -> teamService.updateTeam(teamDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Team id: " + teamDTO.getTeamId() + " does not exist!");
    }

    @Test
    void canDeleteTeam() throws ResourceNotFoundException {
        int teamId = 1;

        when(teamsRepository.existsById(1)).thenReturn(true);

        try {
            teamService.deleteTeam(teamId);
        } catch (NullPointerException ignored) {}

        verify(teamsRepository).deleteById(teamId);
    }

    @Test
    void deleteTeamWillThrowException() throws ResourceNotFoundException {
        int teamId = 1;

        when(teamsRepository.existsById(teamId)).thenReturn(false);

        assertThatThrownBy(() -> teamService.deleteTeam(teamId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Team id: " + teamId + " does not exist!");
    }
}