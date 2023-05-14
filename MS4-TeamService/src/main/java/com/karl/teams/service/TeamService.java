package com.karl.teams.service;

import com.karl.teams.exceptions.PlayerAssignmentException;
import com.karl.teams.exceptions.ResourceIdRequiredException;
import com.karl.teams.exceptions.ResourceIdShouldBeNullException;
import com.karl.teams.exceptions.ResourceNotFoundException;
import com.karl.teams.model.dto.PlayerDTO;
import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.model.entities.Team;
import com.karl.teams.repository.TeamsRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.karl.teams.constants.ExceptionMessage.*;
import static com.karl.teams.constants.ResponseMessage.*;
import static com.karl.teams.constants.UriStrings.*;
import static com.karl.teams.utils.TeamDtoConverter.toEntity;

@Service
public class TeamService {

    private final TeamsRepository teamsRepository;
    private final RestTemplate restTemplate;

    public TeamService(TeamsRepository teamsRepository, RestTemplate restTemplate) {
        this.teamsRepository = teamsRepository;
        this.restTemplate = restTemplate;
    }

    public List<Team> getAllTeams() {

        List<PlayerDTO> players = restTemplate
                .exchange(PLAYERS,
                        HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<PlayerDTO>>() {})
                .getBody();

        List<Team> teams = teamsRepository.findAll();

        if (teams.isEmpty() || players == null || players.isEmpty())
            return teams;

        for (Team t: teams) {
            t.setPlayers(players
                    .stream()
                    .filter(playerDTO -> playerDTO.getPlayerId() != null && playerDTO.getTeamId().equals(t.getTeamId()))
                    .collect(Collectors.toSet()));
        }
        return teams;
    }

    public Team getTeamById(int teamId) throws ResourceNotFoundException {

        Optional<Team> team = teamsRepository.findById(teamId);

        if (team.isEmpty())
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,teamId));

        // TODO: 5/14/2023 : Replace with feign client 
        List<PlayerDTO> players = restTemplate
                .exchange(String.format(PLAYERS_BY_TEAM_ID,teamId),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<PlayerDTO>>() {})
                .getBody();

        team.get().setPlayers(new HashSet<>(players));

        return team.get();
    }

    public Team createTeam(TeamDTO teamDTO) throws ResourceIdShouldBeNullException {

        if (teamDTO.getTeamId() != null)
           throw new ResourceIdShouldBeNullException(ID_SHOULD_BE_NULL_ON_CREATE);

        return teamsRepository.save(toEntity(teamDTO));
    }

    public Team updateTeam(TeamDTO teamDTO) throws ResourceNotFoundException, ResourceIdRequiredException {
        if (teamDTO.getTeamId() == null)
            throw new ResourceIdRequiredException(ID_REQUIRED);

        if (!teamsRepository.existsById(teamDTO.getTeamId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,teamDTO.getTeamId()));

        return teamsRepository.save(toEntity(teamDTO));
    }

    public String deleteTeam(int teamId) throws ResourceNotFoundException {

        if (teamId < 1)
            throw new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO);

        if(!teamsRepository.existsById(teamId))
            throw new ResourceNotFoundException(
                    String.format(RESOURCE_NOT_FOUND,teamId));

        teamsRepository.deleteById(teamId);

        return String.format(teamsRepository.existsById(teamId)  ? DELETE_MESSAGE_FAILED : DELETE_MESSAGE_SUCCESS, teamId);
    }
    public String assignPlayerToTeam(PlayerDTO playerDTO) throws PlayerAssignmentException {

        if (playerDTO.getPlayerId() == null)
            throw new PlayerAssignmentException(PLAYER_ID_REQUIRED);

        if (playerDTO.getTeamId() == null)
            throw new PlayerAssignmentException(TEAM_ID_REQUIRED);

        PlayerDTO player = restTemplate
                .exchange(String.format(PLAYERS_BY_PLAYER_ID,playerDTO.getPlayerId()),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PlayerDTO>() {})
                .getBody();

        if(player != null && playerDTO.getTeamId() != null &&
                player.getTeamId() != null && player.getPlayerId() != null &&
                Objects.equals(player.getTeamId(), playerDTO.getTeamId()))
            throw new PlayerAssignmentException(PLAYER_ALREADY_ASSIGNED);

        PlayerDTO updatedPlayerResponse = restTemplate
                        .exchange(UPDATE_PLAYERS_TEAM,
                                HttpMethod.PUT,
                                new HttpEntity<>(playerDTO),
                                PlayerDTO.class)
                        .getBody();

        if (updatedPlayerResponse == null)
            throw new PlayerAssignmentException(EMPTY_RESPONSE_BODY);

        if(!Objects.equals(updatedPlayerResponse.getTeamId(), playerDTO.getTeamId()))
            throw new PlayerAssignmentException(PLAYER_ASSIGN_FAILED);

        return PLAYER_ASSIGN_SUCCESS;
    }

    public String removePlayerToTeam(PlayerDTO playerDTO) throws PlayerAssignmentException {

        if (playerDTO.getPlayerId() == null)
            throw new PlayerAssignmentException(PLAYER_ID_REQUIRED);

        playerDTO.setTeamId(null);

        PlayerDTO updatedPlayerResponse = restTemplate
                .exchange(UPDATE_PLAYERS_TEAM,
                        HttpMethod.PUT,
                        new HttpEntity<>(playerDTO),
                        PlayerDTO.class)
                .getBody();

        if (updatedPlayerResponse == null)
            throw new PlayerAssignmentException(EMPTY_RESPONSE_BODY);

        if (updatedPlayerResponse.getTeamId() != null)
            throw new PlayerAssignmentException(PLAYER_REMOVAL_FAILED);

        return PLAYER_REMOVAL_SUCCESS;
    }


}
