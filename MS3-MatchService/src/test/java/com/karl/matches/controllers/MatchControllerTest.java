package com.karl.matches.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karl.matches.dto.*;
import com.karl.matches.entity.Match;
import com.karl.matches.exception.*;
import com.karl.matches.service.MatchService;
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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.karl.matches.constants.ExceptionMessages.*;
import static com.karl.matches.constants.ResponseMessage.DELETE_MESSAGE_SUCCESS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = MatchController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<MatchDTO> expectedMatchDTOList = new ArrayList<>();
    private MatchDTO expectedMatchDTO;
    private final List<Match> expectedMatchList = new ArrayList<>();
    private Match expectedMatch;
    private final String feignExceptionMessage = "Bad request exception occurred: {}";
    private TournamentDTO Fifa,MLB = new TournamentDTO(1, "FIFA", "Multilevel Tournament", "Soccer");
    private TeamDTO Lakers = new TeamDTO(2, "LA Lakers", Collections.emptySet());
    private TeamDTO Collabera = new TeamDTO(1, "Team Collabera", Collections.emptySet());

    @BeforeEach
    public void setUp() {
        Match m1 = new Match(1,
                2,
                1,
                LocalDateTime.of(2023, Month.JULY, 29, 19, 30, 40),
                1,
                2);

        Match m2 = new Match(2,
                1,
                3,
                LocalDateTime.of(2023, Month.AUGUST, 12, 13, 0, 0),
                3,
                4);

        Match m3 = new Match(3,
                3,
                1,
                LocalDateTime.of(2023, Month.DECEMBER, 25, 8, 0, 0),
                1,
                4);

        expectedMatchList.addAll(List.of(m1,m2,m3));
        expectedMatch = expectedMatchList.get(0);

        Fifa = new TournamentDTO(1, "FIFA", "Multilevel Tournament", "Soccer");
        MLB = new TournamentDTO(3,"MLB","Play-In Tournament","Baseball");
        Lakers = new TeamDTO(2, "LA Lakers", Collections.emptySet());
        Collabera = new TeamDTO(1, "Team Collabera", Collections.emptySet());

        MatchDTO mdto1 = new MatchDTO(1
                ,new FieldDTO(2,"Staples Center","Los Angeles, California",18997),
                Fifa,
                LocalDateTime.of(2023, Month.JULY, 29, 19, 30, 40),
                Collabera,
                Lakers
        );

        MatchDTO mdto2 = new MatchDTO(2,
                new FieldDTO(3,"Manchester Arena","United Kingdom, California",21000),
                MLB,
                LocalDateTime.of(2023, Month.AUGUST, 12, 13, 0, 0),
                new TeamDTO(3,"Boston Celtics", Collections.emptySet()),
                new TeamDTO(4,"Team Cognixia", Collections.emptySet()));

        MatchDTO mdto3 = new MatchDTO(3,
                new FieldDTO(1,"Madison Square Garden","New York City, New York",19812),
                Fifa,
                LocalDateTime.of(2023, Month.DECEMBER, 25, 8, 0, 0),
                Collabera,
                Lakers);

        expectedMatchDTOList.addAll(List.of(mdto1,mdto2,mdto3));
        expectedMatchDTO = expectedMatchDTOList.get(0);
    }

    @Test
    void canGetAllMatches() throws Exception {

        when(matchService.getAllMatches()).thenReturn(expectedMatchDTOList);

        // Perform the GET request and validate the response
        ResultActions res = mockMvc.perform(get("/api/v1/matches/"));

        for (int i = 0; i < expectedMatchDTOList.size(); i++) {
            res.andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$["+ i +"].matchId").value(expectedMatchDTOList.get(i).getMatchId()))
                    .andExpect(jsonPath("$["+ i +"].tournamentDTO").value(expectedMatchDTOList.get(i).getTournamentDTO()))
                    .andExpect(jsonPath("$["+ i +"].homeTeamDto.teamId").value(expectedMatchDTOList.get(i).getHomeTeamDto().getTeamId()))
                    .andExpect(jsonPath("$["+ i +"].awayTeamDto.teamId").value(expectedMatchDTOList.get(i).getAwayTeamDto().getTeamId()));
        }
        // Verify that the userService method was called
        verify(matchService, times(1)).getAllMatches();
    }

    @Test
    void getMatchWillThrowNotFoundException()
            throws ResourceNotFoundException,
            FeignClientUnavailableException {

        String exceptionMessage = String.format(RESOURCE_NOT_FOUND,expectedMatchDTO.getMatchId());

        when(matchService.getByMatchId(expectedMatchDTO.getMatchId()))
                .thenThrow(new ResourceNotFoundException(exceptionMessage));

        ResultActions response;
        try {
            response = mockMvc.perform(get("/api/v1/matches/" + expectedMatchDTO.getMatchId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedMatchDTO)));

            response.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(exceptionMessage))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getMatchWillThrowFeignClientException()
            throws ResourceNotFoundException,
            FeignClientUnavailableException {

        when(matchService.getByMatchId(expectedMatchDTO.getMatchId()))
                .thenThrow(new FeignClientUnavailableException(FEIGN_EXCEPTION));

        ResultActions response;
        try {
            response = mockMvc.perform(get("/api/v1/matches/" + expectedMatchDTO.getMatchId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedMatchDTO)));

            response.andExpect(status().isServiceUnavailable())
                    .andExpect(jsonPath("$.message").value(FEIGN_EXCEPTION))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void canGetMatch() throws Exception {
        when(matchService.getByMatchId(expectedMatchDTO.getMatchId())).thenReturn(expectedMatchDTO);

        // Perform the GET request and validate the response
        ResultActions res = mockMvc.perform(get("/api/v1/matches/" + expectedMatchDTO.getMatchId()));

        res.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.matchId").value(expectedMatchDTO.getMatchId()))
                .andExpect(jsonPath("$.tournamentDTO").value(expectedMatchDTO.getTournamentDTO()))
                .andExpect(jsonPath("$.homeTeamDto.teamId").value(expectedMatchDTO.getHomeTeamDto().getTeamId()))
                .andExpect(jsonPath("$.awayTeamDto.teamId").value(expectedMatchDTO.getAwayTeamDto().getTeamId()));

        // Verify that the userService method was called
        verify(matchService, times(1)).getByMatchId(expectedMatchDTO.getMatchId());
    }

    @Test
    void createMatchWillThrowException ()
            throws ResourceIdShouldBeNullException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {

        when(matchService.createMatch(expectedMatchDTO))
                .thenThrow(new ResourceIdShouldBeNullException(ID_SHOULD_BE_NULL_ON_CREATE));

        ResultActions response;
        try {
            response = mockMvc.perform(post("/api/v1/matches/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedMatchDTO)));

            response.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ID_SHOULD_BE_NULL_ON_CREATE))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createMatchWillReturnCreatedValue()
            throws ResourceIdShouldBeNullException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {

        when(matchService.createMatch(expectedMatchDTO)).thenReturn(expectedMatchDTO);

        ResultActions response;
        try {
            response = mockMvc.perform(post("/api/v1/matches/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedMatchDTO)));

            response.andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.matchId").value(expectedMatchDTO.getMatchId()))
                    .andExpect(jsonPath("$.tournamentDTO").value(expectedMatchDTO.getTournamentDTO()))
                    .andExpect(jsonPath("$.homeTeamDto.teamId").value(expectedMatchDTO.getHomeTeamDto().getTeamId()))
                    .andExpect(jsonPath("$.awayTeamDto.teamId").value(expectedMatchDTO.getAwayTeamDto().getTeamId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Verify that the userService method was called
        verify(matchService, times(1)).createMatch(expectedMatchDTO);
    }

    @Test
    void updateMatchWillThrowResourceIdRequiredException()
            throws ResourceIdRequiredException,
            ResourceNotFoundException,
            MatchCreateException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {

        when(matchService.updateMatch(expectedMatchDTO))
                .thenThrow(new ResourceIdRequiredException(ID_REQUIRED));

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/matches/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedMatchDTO)));

            response.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ID_REQUIRED))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateMatchWillThrowResourceNotFoundException()
            throws ResourceIdRequiredException, ResourceNotFoundException,
            MatchCreateException, FeignClientUnavailableException, MissingOrNonExistentFieldException {

        when(matchService.updateMatch(expectedMatchDTO))
                .thenThrow(new ResourceNotFoundException(
                        String.format(RESOURCE_NOT_FOUND,expectedMatchDTO.getMatchId())));

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/matches/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedMatchDTO)));

            response.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message")
                            .value(String.format(RESOURCE_NOT_FOUND,expectedMatchDTO.getMatchId())))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateMatchWillReturnCreatedValue()
            throws ResourceIdRequiredException,
            ResourceNotFoundException,
            MatchCreateException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {

        when(matchService.updateMatch(expectedMatchDTO)).thenReturn(expectedMatchDTO);

        ResultActions response;
        try {
            response = mockMvc.perform(put("/api/v1/matches/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedMatchDTO)));

            response.andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.matchId").value(expectedMatchDTO.getMatchId()))
                    .andExpect(jsonPath("$.tournamentDTO").value(expectedMatchDTO.getTournamentDTO()))
                    .andExpect(jsonPath("$.homeTeamDto.teamId").value(expectedMatchDTO.getHomeTeamDto().getTeamId()))
                    .andExpect(jsonPath("$.awayTeamDto.teamId").value(expectedMatchDTO.getAwayTeamDto().getTeamId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Verify that the userService method was called
        verify(matchService, times(1)).updateMatch(expectedMatchDTO);
    }

    @Test
    void deleteMatchWillThrowResourceNotFoundException() throws ResourceNotFoundException {
        int negativeId = 986;
        when(matchService.deleteMatch(negativeId))
                .thenThrow(new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO));

        ResultActions response;
        try {
            response = mockMvc.perform(delete("/api/v1/matches/delete/" + negativeId)
                    .contentType(MediaType.APPLICATION_JSON));

            response.andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(ID_PARAM_LESS_THAN_ZERO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteMatchWillThrowResourceNotFoundExceptionTeamMissingOnDatabase()
            throws ResourceNotFoundException {

        int nonExistentTeamId = 986;
        when(matchService.deleteMatch(nonExistentTeamId))
                .thenThrow(new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,nonExistentTeamId)));

        ResultActions response;
        try {
            response = mockMvc.perform(delete("/api/v1/matches/delete/" + nonExistentTeamId)
                    .contentType(MediaType.APPLICATION_JSON));

            response.andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message")
                            .value(String.format(RESOURCE_NOT_FOUND,nonExistentTeamId)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void canDeleteMatch() throws ResourceNotFoundException {

        when(matchService.deleteMatch(expectedMatch.getMatchId())).thenReturn(DELETE_MESSAGE_SUCCESS);

        ResultActions response;
        try {
            response = mockMvc.perform(delete("/api/v1/matches/delete/" + expectedMatchDTO.getMatchId())
                    .contentType(MediaType.APPLICATION_JSON));

            response.andExpect(status().isOk())
                    .andExpect(content().contentType("text/plain;charset=UTF-8"))
                    .andExpect(jsonPath("$").value(DELETE_MESSAGE_SUCCESS));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
