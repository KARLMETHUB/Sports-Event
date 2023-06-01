package com.karl.teams.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karl.teams.exceptions.PlayerAssignmentException;
import com.karl.teams.exceptions.ResourceIdRequiredException;
import com.karl.teams.exceptions.ResourceIdShouldBeNullException;
import com.karl.teams.exceptions.ResourceNotFoundException;
import com.karl.teams.model.dto.PlayerDTO;
import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.model.entities.Team;
import com.karl.teams.service.TeamService;
import com.karl.teams.utils.TeamDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.karl.teams.constants.ExceptionMessage.*;
import static com.karl.teams.constants.ResponseMessage.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TeamController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<TeamDTO> teamDTOs;
    private List<Team> teams;

    private TeamDTO teamDTO;
    private Team team;
    private PlayerDTO playerDTO;

    @BeforeEach
    public void init() {
        teamDTOs = new ArrayList<>();

        teamDTOs.add(new TeamDTO(1,"Test", Collections.emptySet()));
        teamDTOs.add(new TeamDTO(2,"Test2", Collections.emptySet()));
        teamDTOs.add(new TeamDTO(3,"Test3", Collections.emptySet()));

        teams = new ArrayList<>();
        teams.addAll(teamDTOs.stream().map(TeamDtoConverter::toEntity).collect(Collectors.toList()));

        teamDTO = teamDTOs.get(0);
        team = teams.get(0);

        playerDTO = new PlayerDTO(1,"Player fname","Player lname","PH",null);
    }

    @Test
    void canGetAllTeams() throws Exception {

        when(teamService.getAllTeams()).thenReturn(teams);

        // Perform the GET request and validate the response
        ResultActions res = mockMvc.perform(get("/api/v1/teams/"));

        for (int i = 0; i < teamDTOs.size(); i++) {
            res.andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$["+ i +"].teamId").value(teamDTOs.get(i).getTeamId()))
                    .andExpect(jsonPath("$["+ i +"].teamName").value(teamDTOs.get(i).getTeamName()));
        }
        // Verify that the userService method was called
        verify(teamService, times(1)).getAllTeams();
    }

    @Test
    void canGetTeam() throws Exception {

        when(teamService.getTeamById(teamDTO.getTeamId())).thenReturn(team);

        // Perform the GET request and validate the response
        ResultActions res = mockMvc.perform(get("/api/v1/teams/" + teamDTO.getTeamId()));

        res.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.teamId").value(teamDTO.getTeamId()))
                .andExpect(jsonPath("$.teamName").value(teamDTO.getTeamName()));

        // Verify that the userService method was called
        verify(teamService, times(1)).getTeamById(teamDTO.getTeamId());
    }

    @Test
    void getTeamWillThrowNotFoundException() throws ResourceNotFoundException {

        String exceptionMessage = String.format(RESOURCE_NOT_FOUND,teamDTO.getTeamId());

        when(teamService.getTeamById(teamDTO.getTeamId())).thenThrow(new ResourceNotFoundException(exceptionMessage));

        ResultActions response;
        try {
            response = mockMvc.perform(get("/api/v1/teams/" + teamDTO.getTeamId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(teamDTO)));

            response.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(exceptionMessage))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void createTeamWillReturnCreatedValue()
            throws ResourceIdShouldBeNullException {


        when(teamService.createTeam(teamDTO)).thenReturn(team);

        ResultActions response;
        try {
            response = mockMvc.perform(post("/api/v1/teams/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(teamDTO)));

            response.andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.teamId").value(teamDTO.getTeamId()))
                    .andExpect(jsonPath("$.teamName").value(teamDTO.getTeamName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Verify that the userService method was called
        verify(teamService, times(1)).createTeam(teamDTO);
    }


    @Test
    void createTeamWillThrowException () throws ResourceIdShouldBeNullException {

        when(teamService.createTeam(teamDTO)).thenThrow(new ResourceIdShouldBeNullException(ID_SHOULD_BE_NULL_ON_CREATE));

        ResultActions response;
        try {
            response = mockMvc.perform(post("/api/v1/teams/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(teamDTO)));

            response.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ID_SHOULD_BE_NULL_ON_CREATE))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateTeamWillReturnCreatedValue()
            throws ResourceIdRequiredException,
            ResourceNotFoundException {

        when(teamService.updateTeam(teamDTO)).thenReturn(teams.get(0));

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/teams/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(teamDTOs.get(0))));

            response.andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.teamId").value(teamDTO.getTeamId()))
                    .andExpect(jsonPath("$.teamName").value(teamDTO.getTeamName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Verify that the userService method was called
        verify(teamService, times(1)).updateTeam(teamDTO);
    }

    @Test
    void updateTeamWillThrowResourceIdRequiredException () throws
            ResourceIdRequiredException, ResourceNotFoundException {

        when(teamService.updateTeam(teamDTO)).thenThrow(new ResourceIdRequiredException(ID_REQUIRED));

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/teams/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(teamDTO)));

            response.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ID_REQUIRED))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateTeamWillThrowResourceNotFoundException()
            throws ResourceIdRequiredException, ResourceNotFoundException {

        when(teamService.updateTeam(teamDTO)).thenThrow(new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,teamDTO.getTeamId())));

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/teams/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(teamDTO)));

            response.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(String.format(RESOURCE_NOT_FOUND,teamDTO.getTeamId())))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void canDeleteTeam() throws ResourceNotFoundException {

        when(teamService.deleteTeam(teamDTO.getTeamId())).thenReturn(DELETE_MESSAGE_SUCCESS);

        ResultActions response;
        try {
            response = mockMvc.perform(delete("/api/v1/teams/delete/" + teamDTO.getTeamId())
                    .contentType(MediaType.APPLICATION_JSON));

            response.andExpect(status().isOk())
                    .andExpect(content().contentType("text/plain;charset=UTF-8"))
                    .andExpect(jsonPath("$").value(DELETE_MESSAGE_SUCCESS));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteTeamWillThrowResourceNotFoundException() throws ResourceNotFoundException {
        int negativeTeamId = 986;
        when(teamService.deleteTeam(negativeTeamId))
                .thenThrow(new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO));

        ResultActions response;
        try {
            response = mockMvc.perform(delete("/api/v1/teams/delete/" + negativeTeamId)
                    .contentType(MediaType.APPLICATION_JSON));

            response.andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(ID_PARAM_LESS_THAN_ZERO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteTeamWillThrowResourceNotFoundExceptionTeamMissingOnDatabase()
            throws ResourceNotFoundException {

        int nonExistentTeamId = 986;
        when(teamService.deleteTeam(nonExistentTeamId))
                .thenThrow(new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,nonExistentTeamId)));

        ResultActions response;
        try {
            response = mockMvc.perform(delete("/api/v1/teams/delete/" + nonExistentTeamId)
                    .contentType(MediaType.APPLICATION_JSON));

            response.andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(String.format(RESOURCE_NOT_FOUND,nonExistentTeamId)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void canAssignPlayer() throws PlayerAssignmentException {
        playerDTO.setTeamId(1);

        when(teamService.assignPlayerToTeam(playerDTO))
                .thenReturn(PLAYER_ASSIGN_SUCCESS);

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/teams/assignPlayer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(playerDTO)));

            response.andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(PLAYER_ASSIGN_SUCCESS))
                    .andExpect(content().contentType("text/plain;charset=UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void assignPlayerWillThrowPlayerAssignmentException()
            throws PlayerAssignmentException {
        assignRemovePlayerWithReturnException(true,PLAYER_ID_REQUIRED);
    }

    @Test
    void assignPlayerWillThrowPlayerAssignmentExceptionTeamRequired()
            throws PlayerAssignmentException {
        assignRemovePlayerWithReturnException(true,TEAM_ID_REQUIRED);
    }
    @Test
    void assignPlayerWillThrowPlayerAssignmentExceptionAlreadyAssigned()
            throws PlayerAssignmentException {
        assignRemovePlayerWithReturnException(true,PLAYER_ALREADY_ASSIGNED);
    }
    @Test
    void assignPlayerWillThrowPlayerAssignmentEmptyBody()
            throws PlayerAssignmentException {
        assignRemovePlayerWithReturnException(true,EMPTY_RESPONSE_BODY);
    }



    @Test
    void canRemovePlayer() throws PlayerAssignmentException {
        playerDTO.setTeamId(1);

        when(teamService.removePlayerToTeam(playerDTO))
                .thenReturn(PLAYER_REMOVAL_SUCCESS);

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/teams/removePlayer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(playerDTO)));

            response.andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(PLAYER_REMOVAL_SUCCESS))
                    .andExpect(content().contentType("text/plain;charset=UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removePlayerWillThrowPlayerAssignmentIdRequired()
            throws PlayerAssignmentException {
        assignRemovePlayerWithReturnException(false,PLAYER_ID_REQUIRED);
    }

    @Test
    void removePlayerWillThrowPlayerAssignmentUpdateFailed()
            throws PlayerAssignmentException {
        assignRemovePlayerWithReturnException(false,PLAYER_REMOVAL_FAILED);
    }

    @Test
    void removePlayerWillThrowPlayerAssignmentEmptyBody()
            throws PlayerAssignmentException {
        assignRemovePlayerWithReturnException(false,EMPTY_RESPONSE_BODY);
    }


    private void assignRemovePlayerWithReturnException(boolean assign,String s)
            throws PlayerAssignmentException {

        when(assign ?
                teamService.assignPlayerToTeam(playerDTO)
                : teamService.removePlayerToTeam(playerDTO))
                .thenThrow(new PlayerAssignmentException(s));

        ResultActions response;
        try {
            response = mockMvc.perform(
                    put(assign ?
                            "/api/v1/teams/assignPlayer" : "/api/v1/teams/removePlayer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(playerDTO)));

            response.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(s))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}