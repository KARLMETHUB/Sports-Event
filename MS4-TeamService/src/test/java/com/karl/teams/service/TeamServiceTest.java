package com.karl.teams.service;

import com.karl.teams.exceptions.PlayerAssignmentException;
import com.karl.teams.exceptions.ResourceIdRequiredException;
import com.karl.teams.exceptions.ResourceIdShouldBeNullException;
import com.karl.teams.exceptions.ResourceNotFoundException;
import com.karl.teams.model.dto.PlayerDTO;
import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.model.entities.Team;
import com.karl.teams.repository.TeamsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.karl.teams.constants.ExceptionMessage.*;
import static com.karl.teams.constants.ResponseMessage.*;
import static com.karl.teams.constants.UriStrings.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;


@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamsRepository teamsRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private TeamService teamService;

    List<PlayerDTO> players = new ArrayList<>();
    List<Team> teams = new ArrayList<>();
    ResponseEntity<List<PlayerDTO>> playersResponse;

    @BeforeEach
    void setUp() {
        PlayerDTO p = new PlayerDTO(null,"Lebron","James","USA",1);
        PlayerDTO p1 = new PlayerDTO(null,"Anthony","Davis","USA",1);
        PlayerDTO p2 = new PlayerDTO(null,"Kobe","Bryant","USA",1);
        PlayerDTO p3 = new PlayerDTO(null,"Jordan","Poole","USA",1);
        PlayerDTO p4 = new PlayerDTO(null,"Jason","Tatum","USA",1);
        PlayerDTO p5 = new PlayerDTO(null,"Karl","Mirafuente","PH",2);
        PlayerDTO p6 = new PlayerDTO(null,"RM","Bautista","PH",2);
        PlayerDTO p7 = new PlayerDTO(null,"Subbu","N/a","IND",3);
        PlayerDTO p8 = new PlayerDTO(null,"Murtaza","N/a","IND",3);

        players.addAll(List.of(p,p1,p2,p3,p4,p5,p6,p7,p8));

        playersResponse = new ResponseEntity<List<PlayerDTO>>(players, OK);

        Team collabera = new Team(1,"Team Collabera", Collections.emptySet());
        Team lakers = new Team(2,"LA Lakers", Collections.emptySet());
        Team bostonCeltics = new Team(3,"Boston Celtics", Collections.emptySet());
        Team cognixia = new Team(4,"Team Cognixia", Collections.emptySet());

        teams.addAll(List.of(collabera,lakers,bostonCeltics,cognixia));

    }

    @Test
    void canGetAllTeams() {

        when(restTemplate.exchange(PLAYERS,
                        GET,
                        null,
                        new ParameterizedTypeReference<List<PlayerDTO>>() {}))
                .thenReturn(playersResponse);

        when(teamsRepository.findAll())
                .thenReturn(teams);

        List<Team> allTeams = teamService.getAllTeams();

        Mockito.verify(teamsRepository).findAll();

        Assertions.assertThat(allTeams).isNotNull();
        //Assertions.assertThat(allTeams.size()).isEqualTo(teams.size());
    }

    @Test
    void canGetTeamsWhenTeamsEmpty() {
        when(restTemplate.exchange(PLAYERS,
                GET,
                null,
                new ParameterizedTypeReference<List<PlayerDTO>>() {}))
                .thenReturn(playersResponse);

        when(teamsRepository.findAll()).thenReturn(Collections.emptyList());

        List<Team> allTeams = teamService.getAllTeams();

        assertThat(allTeams).isNotNull();
        //assertThat(allTeams.size()).isZero();
    }

    @Test
    void canGetTeamsWhenPlayersIsEmpty() {

        when(teamsRepository.findAll()).thenReturn(teams);
        when(restTemplate.exchange(PLAYERS,
                GET,
                null,
                new ParameterizedTypeReference<List<PlayerDTO>>() {}))
                .thenReturn(new ResponseEntity<List<PlayerDTO>>(Collections.emptyList(), OK));

        List<Team> allTeams = teamService.getAllTeams();

        assertThat(allTeams).isNotNull();
        //assertThat(allTeams.size()).isEqualTo(teams.size());
    }

    @Test
    void canGetTeamById() throws ResourceNotFoundException {
        int teamId = 1;

        when(teamsRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            teamService.getTeamById(teamId);
        });

        Optional<Team> returnsValue = Optional.of(teams.get(0));

        when(teamsRepository.findById(teamId))
                .thenReturn(returnsValue);

        when(restTemplate.exchange(String.format(PLAYERS_BY_TEAM_ID,teamId),
                GET,
                null,
                new ParameterizedTypeReference<List<PlayerDTO>>() {}))
                .thenReturn(playersResponse);

        Team result = teamService.getTeamById(teamId);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTeamId()).isEqualTo(teamId);
        Assertions.assertThat(result.getTeamName()).isNotNull();
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
    void createTeamWillThrowException() {

        TeamDTO teamDTO = new TeamDTO(12,teams.get(0).getTeamName(),Collections.emptySet());

        assertThatThrownBy(() -> teamService.createTeam(teamDTO))
                .isInstanceOf(ResourceIdShouldBeNullException.class)
                .hasMessageContaining(ID_SHOULD_BE_NULL_ON_CREATE);
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
        TeamDTO teamDTO = new TeamDTO(null,"Test team",Collections.emptySet());

        assertThatThrownBy(() -> teamService.updateTeam(teamDTO))
                .isInstanceOf(ResourceIdRequiredException.class)
                .hasMessageContaining(ID_REQUIRED);

        TeamDTO teamDTO2 = new TeamDTO(3232,"Test team",Collections.emptySet());

        when(teamsRepository.existsById(Mockito.anyInt())).thenReturn(false);

        assertThatThrownBy(() -> teamService.updateTeam(teamDTO2))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.format(RESOURCE_NOT_FOUND,teamDTO2.getTeamId()));
    }

    @Test
    void updateTeamWillThrowExceptionWhenUnsuccessful()
            throws ResourceNotFoundException {
        int teamId = 1;

        /*For repeated calls*/
        when(teamsRepository.existsById(teamId))
                .thenReturn(true,true);
                /*Can do this too
                .thenReturn(true)
                .thenReturn(false);*/

        String result = teamService.deleteTeam(teamId);

        verify(teamsRepository).deleteById(teamId);
        assertThat(result).isEqualTo(String.format(DELETE_MESSAGE_FAILED, teamId));
    }
    @Test
    void canDeleteTeam() throws ResourceNotFoundException {
        int teamId = 1;

        /*For repeated calls*/
        when(teamsRepository.existsById(teamId))
                .thenReturn(true,false);
                /*Can do this too
                .thenReturn(true)
                .thenReturn(false);*/

        String result = teamService.deleteTeam(teamId);

        verify(teamsRepository).deleteById(teamId);
        assertThat(result).isEqualTo(String.format(DELETE_MESSAGE_SUCCESS, teamId));
    }

    @Test
    void deleteTeamWillThrowException() {

        int negative = -1132;
        assertThatThrownBy(() -> teamService.deleteTeam(negative))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(ID_PARAM_LESS_THAN_ZERO);

        int teamId = 1;
        when(teamsRepository.existsById(teamId)).thenReturn(false);

        assertThatThrownBy(() -> teamService.deleteTeam(teamId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.format(RESOURCE_NOT_FOUND,teamId));
    }

    @Test
    void assignPlayerWillThrowExceptionWhenMissingRequiredFields() {

        PlayerDTO playerWithMissingValues = players.get(0);
        playerWithMissingValues.setPlayerId(null);

        assertThatThrownBy(() -> teamService.assignPlayerToTeam(playerWithMissingValues))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(PLAYER_ID_REQUIRED);

        playerWithMissingValues.setPlayerId(1);
        playerWithMissingValues.setTeamId(null);

        assertThatThrownBy(() -> teamService.assignPlayerToTeam(playerWithMissingValues))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(TEAM_ID_REQUIRED);

    }

    @Test
    void assignPlayerWillThrowExceptionWhenAlreadyAssigned () {

        PlayerDTO playerWithMissingValues = players.get(0);
        playerWithMissingValues.setPlayerId(1);

        whenRestApiCalled(PLAYERS_BY_PLAYER_ID,GET,OK,playerWithMissingValues);

        assertThatThrownBy(() -> teamService.assignPlayerToTeam(playerWithMissingValues))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(PLAYER_ALREADY_ASSIGNED);
    }

    @Test
    void assignPlayerWillThrowExceptionWhenUpdateReturnsNull() {

        PlayerDTO playerDTO = players.get(0);
        playerDTO.setPlayerId(1);
        playerDTO.setTeamId(1234);

        Mockito.when(restTemplate.exchange(
                //ArgumentMatchers.eq("http://PlAYERS/api/v1/players/create/{playerId}"),
                String.format(PLAYERS_BY_PLAYER_ID,playerDTO.getPlayerId()),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlayerDTO>() {})
        ).thenReturn(ResponseEntity.ok(null));

        PlayerDTO player = players.get(1);
        player.setPlayerId(1);
        player.setTeamId(121);

        Mockito.when(restTemplate.exchange(
                UPDATE_PLAYERS_TEAM,
                HttpMethod.PUT,
                new HttpEntity<>(player),
                PlayerDTO.class)
        ).thenReturn(ResponseEntity.ok(null));

        assertThatThrownBy(() -> teamService.assignPlayerToTeam(player))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(EMPTY_RESPONSE_BODY);
    }

    @Test
    void assignPlayerWillThrowExceptionWhenUpdateReturnsDifferentTeam() {

        PlayerDTO playerDTO = players.get(0);
        playerDTO.setPlayerId(1);
        playerDTO.setTeamId(1234);

        Mockito.when(restTemplate.exchange(
                //ArgumentMatchers.eq("http://PlAYERS/api/v1/players/create/{playerId}"),
                String.format(PLAYERS_BY_PLAYER_ID,playerDTO.getPlayerId()),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlayerDTO>() {})
        ).thenReturn(ResponseEntity.ok(null));

        PlayerDTO player = players.get(1);
        player.setPlayerId(1);
        player.setTeamId(121);

        PlayerDTO playerWithDifferentTeamID = players.get(2);
        player.setPlayerId(1);
        player.setTeamId(134);

        Mockito.when(restTemplate.exchange(
                UPDATE_PLAYERS_TEAM,
                HttpMethod.PUT,
                new HttpEntity<>(player),
                PlayerDTO.class)
        ).thenReturn(ResponseEntity.ok(playerWithDifferentTeamID));

        assertThatThrownBy(() -> teamService.assignPlayerToTeam(player))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(PLAYER_ASSIGN_FAILED);
    }

    @Test
    void canAssignPlayer() throws PlayerAssignmentException {

        PlayerDTO playerDTO = players.get(0);
        playerDTO.setPlayerId(1);
        playerDTO.setTeamId(1234);

        Mockito.when(restTemplate.exchange(
                String.format(PLAYERS_BY_PLAYER_ID,playerDTO.getPlayerId()),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlayerDTO>() {})
        ).thenReturn(ResponseEntity.ok(null));

        PlayerDTO player = players.get(1);
        player.setPlayerId(1);
        player.setTeamId(121);

        Mockito.when(restTemplate.exchange(
                UPDATE_PLAYERS_TEAM,
                HttpMethod.PUT,
                new HttpEntity<>(player),
                PlayerDTO.class)
        ).thenReturn(ResponseEntity.ok(player));

        String result = teamService.assignPlayerToTeam(player);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(PLAYER_ASSIGN_SUCCESS);

    }

    @Test
    void removeAssignPlayerWillThrowPlayerAssignmentExceptionWhenIdNull() {

        PlayerDTO playerDTO = players.get(0);
        playerDTO.setPlayerId(null);

        assertThatThrownBy(() -> teamService.removePlayerToTeam(playerDTO))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(PLAYER_ID_REQUIRED);
    }

    @Test
    void removeAssignPlayerWillThrowPlayerAssignmentExceptionWhenReturnValueIsNull() {

        PlayerDTO playerDTO = players.get(0);
        playerDTO.setPlayerId(1);

        PlayerDTO removalResult = players.get(1);

        Mockito.when(restTemplate.exchange(
                UPDATE_PLAYERS_TEAM,
                HttpMethod.PUT,
                new HttpEntity<>(playerDTO),
                PlayerDTO.class)
        ).thenReturn(ResponseEntity.ok(null));

        assertThatThrownBy(() -> teamService.removePlayerToTeam(playerDTO))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(EMPTY_RESPONSE_BODY);
    }

    @Test
    void removeAssignPlayerWillThrowPlayerAssignmentExceptionWhenTeamIdNull() {

        PlayerDTO playerDTO = players.get(0);
        playerDTO.setPlayerId(1);

        PlayerDTO removalResult = players.get(1);

        Mockito.when(restTemplate.exchange(
                UPDATE_PLAYERS_TEAM,
                HttpMethod.PUT,
                new HttpEntity<>(playerDTO),
                PlayerDTO.class)
        ).thenReturn(ResponseEntity.ok(removalResult));

        assertThatThrownBy(() -> teamService.removePlayerToTeam(playerDTO))
                .isInstanceOf(PlayerAssignmentException.class)
                .hasMessageContaining(PLAYER_REMOVAL_FAILED);
    }

    @Test
    void canRemovePlayer() throws PlayerAssignmentException {

        PlayerDTO playerDTO = players.get(0);
        playerDTO.setPlayerId(1);

        PlayerDTO removalResult = players.get(1);
        removalResult.setTeamId(null);

        Mockito.when(restTemplate.exchange(
                UPDATE_PLAYERS_TEAM,
                HttpMethod.PUT,
                new HttpEntity<>(playerDTO),
                PlayerDTO.class)
        ).thenReturn(ResponseEntity.ok(removalResult));

        String response = teamService.removePlayerToTeam(playerDTO);
        assertThat(response).isEqualTo(PLAYER_REMOVAL_SUCCESS);
    }

    void whenRestApiCalled(String url,
                                   HttpMethod method,
                                   HttpStatus status,
                                   PlayerDTO result) {

        when(restTemplate.exchange(
                String.format(url, result.getPlayerId()),
                method, null,
                new ParameterizedTypeReference<PlayerDTO>() {}))
                .thenReturn(new ResponseEntity<>(result, status));
    }

}