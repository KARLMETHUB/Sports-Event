package com.karl.teams.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.service.TeamService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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

    @BeforeEach
    public void init() {
        teamDTOs = new ArrayList<>();
        teamDTOs.add(new TeamDTO(1,"Test", Collections.emptySet()));
        teamDTOs.add(new TeamDTO(2,"Test2", Collections.emptySet()));
        teamDTOs.add(new TeamDTO(3,"Test3", Collections.emptySet()));
    }

    @Test
    void getAllTeams() throws Exception {
        Mockito.when(teamService.getAllTeams()).thenReturn(teamDTOs);

        ResultActions response = mockMvc.perform(get("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                /* uncomment if you want parameters
                .param("pageSize","10")*/
        );

        response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(teamDTOs.size())));
    }

    @Test
    void createTeamReturnsCreatedStatusAndReturnsDto() throws Exception {
        BDDMockito.
                given(teamService.createTeam(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(
                post("/api/v1/teams/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDTOs.get(0))));

        response
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.teamId").value(notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teamName", CoreMatchers.is(teamDTOs.get(0).getTeamName())))
                /* Find MockHttpServletRequest on logs*/
                .andDo(MockMvcResultHandlers.print());
    }

}