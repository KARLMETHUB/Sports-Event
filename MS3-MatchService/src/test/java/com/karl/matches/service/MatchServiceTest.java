package com.karl.matches.service;

import com.karl.matches.dto.FieldDTO;
import com.karl.matches.dto.MatchDTO;
import com.karl.matches.dto.TeamDTO;
import com.karl.matches.dto.TournamentDTO;
import com.karl.matches.entity.Match;
import com.karl.matches.exception.*;
import com.karl.matches.repo.MatchRepository;
import com.karl.matches.utils.FieldValidatorService;
import com.karl.matches.utils.MatchDtoConverter;
import com.karl.matches.utils.RestClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.karl.matches.constants.ExceptionMessages.*;
import static com.karl.matches.constants.ResponseMessage.DELETE_MESSAGE_FAILED;
import static com.karl.matches.constants.ResponseMessage.DELETE_MESSAGE_SUCCESS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@ExtendWith({MockitoExtension.class})
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private RestClientService restClientService;
    @Mock
    private FieldValidatorService fieldValidatorService;
    @InjectMocks
    private MatchService matchService;
    private final List<MatchDTO> expectedMatchDTOList = new ArrayList<>();
    private MatchDTO expectedMatchDTO;
    private final List<Match> expectedMatchList = new ArrayList<>();
    private Match expectedMatch;
    private final String feignExceptionMessage = "Bad request exception occurred: {}";
    private TournamentDTO Fifa,MLB = new TournamentDTO(1, "FIFA", "Multilevel Tournament", "Soccer");
    private TeamDTO Lakers = new TeamDTO(2, "LA Lakers", Collections.emptySet());
    private TeamDTO Collabera = new TeamDTO(1, "Team Collabera", Collections.emptySet());

    @BeforeEach
    void setUp() {
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
    void getAllMatchesWillThrowException() throws FeignClientUnavailableException {

        when(matchRepository.findAll()).thenReturn(expectedMatchList);

        when(restClientService.retrieveAttributeData(expectedMatch))
                .thenThrow(new FeignClientUnavailableException(feignExceptionMessage));

        assertThatThrownBy(() -> matchService.getAllMatches())
                .isInstanceOf(FeignClientUnavailableException.class)
                .hasMessageContaining(feignExceptionMessage);
    }
    @Test
    void canGetMatches() throws FeignClientUnavailableException {

        when(matchRepository.findAll()).thenReturn(expectedMatchList);

        when(restClientService.retrieveAttributeData(expectedMatch))
                .thenReturn(expectedMatchDTO);

        List<MatchDTO> actual = matchService.getAllMatches();

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isNotZero();
    }
    @Test
    void getMatchWillThrowNotFoundException() {

        String expectedExceptionMessage = String.format(RESOURCE_NOT_FOUND,expectedMatch.getMatchId());

        when(matchRepository.findById(expectedMatch.getMatchId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> matchService.getByMatchId(expectedMatch.getMatchId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(expectedExceptionMessage);
    }
    @Test
    void getMatchWillThrowFeignClientException() throws FeignClientUnavailableException {

        when(matchRepository.findById(expectedMatch.getMatchId()))
                .thenReturn(Optional.of(expectedMatch));

        when(restClientService.retrieveAttributeData(expectedMatch))
                .thenThrow(new FeignClientUnavailableException(feignExceptionMessage));

        assertThatThrownBy(() ->
                matchService.getByMatchId(expectedMatch.getMatchId()))
                .isInstanceOf(FeignClientUnavailableException.class)
                .hasMessageContaining(feignExceptionMessage);
    }
    @Test
    void canGetMatch() throws FeignClientUnavailableException, ResourceNotFoundException {

        when(matchRepository.findById(expectedMatch.getMatchId()))
                .thenReturn(Optional.of(expectedMatch));

        when(restClientService.retrieveAttributeData(expectedMatch))
                .thenReturn(expectedMatchDTO);

        MatchDTO m = matchService.getByMatchId(expectedMatch.getMatchId());
        assertThat(m).isNotNull();
        assertThat(m).isEqualTo(expectedMatchDTO);
        assertThat(m.getMatchId()).isEqualTo(expectedMatchDTO.getMatchId());
        assertThat(m.getFieldDTO()).isEqualTo(expectedMatchDTO.getFieldDTO());
        assertThat(m.getTournamentDTO()).isEqualTo(expectedMatchDTO.getTournamentDTO());
        assertThat(m.getHomeTeamDto()).isEqualTo(expectedMatchDTO.getHomeTeamDto());
        assertThat(m.getAwayTeamDto()).isEqualTo(expectedMatchDTO.getAwayTeamDto());
    }
    @Test
    void createMatchWillThrowExceptionWhenNotFound() {

        expectedMatchDTO.setMatchId(1);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(ResourceIdShouldBeNullException.class)
                .hasMessageContaining(ID_SHOULD_BE_NULL_ON_CREATE);
    }
    @Test
    void createMatchWillThrowExceptionWhenFieldIdIsNull()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getFieldDTO().setFieldId(null);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(FIELD_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(FIELD_ID_REQUIRED);
    }

    @Test
    void createMatchWillThrowExceptionWhenFieldIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getFieldDTO().setFieldId(1);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(FIELD_ID_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(FIELD_ID_NON_EXISTENT);
    }

    @Test
    void createMatchWillThrowExceptionWhenTournamentIdIsNull()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getTournamentDTO().setTournamentId(null);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(TOURNAMENT_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(TOURNAMENT_ID_REQUIRED);
    }

    @Test
    void createMatchWillThrowExceptionWhenTournamentIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getFieldDTO().setFieldId(1);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(TOURNAMENT_ID_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(TOURNAMENT_ID_NON_EXISTENT);
    }

    @Test
    void createMatchWillThrowExceptionWhenHomeTeamIdIsNull() throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getHomeTeamDto().setTeamId(null);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(HOME_TEAM_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(HOME_TEAM_ID_REQUIRED);
    }

    @Test
    void createMatchWillThrowExceptionWhenHomeTeamIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getHomeTeamDto().setTeamId(1);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(HOME_TEAM_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(HOME_TEAM_NON_EXISTENT);
    }

    @Test
    void createMatchWillThrowExceptionWhenAwayTeamIdIsNull()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getAwayTeamDto().setTeamId(null);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(AWAY_TEAM_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(AWAY_TEAM_ID_REQUIRED);
    }

    @Test
    void createMatchWillThrowExceptionWhenAwayTeamIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.setMatchId(null);
        expectedMatchDTO.getAwayTeamDto().setTeamId(1);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(AWAY_TEAM_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.createMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(AWAY_TEAM_NON_EXISTENT);
    }

    @Test
    void createMatchWillThrowExceptionSavedButCannotMapToDTO()
            throws FeignClientUnavailableException {

        expectedMatchDTO.setMatchId(null);
        expectedMatch.setMatchId(null);

        when(matchRepository.save(expectedMatch))
                .thenReturn(expectedMatch);

        when(restClientService.retrieveAttributeData(expectedMatch))
                .thenThrow(new FeignClientUnavailableException(feignExceptionMessage));

        try (MockedStatic<MatchDtoConverter> utilities = Mockito.mockStatic(MatchDtoConverter.class)) {
            utilities.when(() -> MatchDtoConverter.toEntity(expectedMatchDTO))
                    .thenReturn(expectedMatch);
            when(MatchDtoConverter.toEntity(expectedMatchDTO)).thenReturn(expectedMatch);

            assertThatThrownBy(() -> matchService.createMatch(expectedMatchDTO))
                    .isInstanceOf(FeignClientUnavailableException.class)
                    .hasMessageContaining(feignExceptionMessage);
        }
    }
    @Test
    void canCreateMatch()
            throws FeignClientUnavailableException,
            ResourceIdShouldBeNullException,
            MissingOrNonExistentFieldException {
        expectedMatchDTO.setMatchId(null);
        expectedMatch.setMatchId(null);

        when(matchRepository.save(expectedMatch))
                .thenReturn(expectedMatch);

        MatchDTO resultMock = new MatchDTO(1
                ,new FieldDTO(2,"Staples Center","Los Angeles, California",18997),
                Fifa,
                LocalDateTime.of(2023, Month.JULY, 29, 19, 30, 40),
                Collabera,
                Lakers
        );

        when(restClientService.retrieveAttributeData(expectedMatch))
                .thenReturn(resultMock);

        try (MockedStatic<MatchDtoConverter> utilities = Mockito.mockStatic(MatchDtoConverter.class)) {
            utilities.when(() -> MatchDtoConverter.toEntity(expectedMatchDTO))
                    .thenReturn(expectedMatch);
                when(MatchDtoConverter.toEntity(expectedMatchDTO)).thenReturn(expectedMatch);

                MatchDTO actual = matchService.createMatch(expectedMatchDTO);

                assertThat(actual).isNotNull();
                assertThat(actual).isEqualTo(resultMock);
                assertThat(actual.getMatchId()).isNotNull();
                assertThat(actual.getMatchId()).isEqualTo(resultMock.getMatchId());
        }
    }


    @Test
    void updateMatchWillThrowExceptionWhenNullId() {

        expectedMatchDTO.setMatchId(null);

        assertThatThrownBy(() -> matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(ResourceIdRequiredException.class)
                .hasMessageContaining(ID_REQUIRED);
    }
    @Test
    void updateMatchWillThrowExceptionWhenNotFound() {

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(false);

        assertThatThrownBy(() -> matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.format(RESOURCE_NOT_FOUND,expectedMatchDTO.getMatchId()));
    }

    @Test
    void updateMatchWillThrowExceptionWhenFieldIdIsNull()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getFieldDTO().setFieldId(null);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(FIELD_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(FIELD_ID_REQUIRED);
    }


    @Test
    void updateMatchWillThrowExceptionWhenFieldIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getFieldDTO().setFieldId(1);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(FIELD_ID_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(FIELD_ID_NON_EXISTENT);
    }

    @Test
    void updateMatchWillThrowExceptionWhenTournamentIdIsNull()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getTournamentDTO().setTournamentId(null);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(TOURNAMENT_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(TOURNAMENT_ID_REQUIRED);
    }

    @Test
    void updateMatchWillThrowExceptionWhenTournamentIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getFieldDTO().setFieldId(1);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(TOURNAMENT_ID_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(TOURNAMENT_ID_NON_EXISTENT);
    }

    @Test
    void updateMatchWillThrowExceptionWhenHomeTeamIdIsNull()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getHomeTeamDto().setTeamId(null);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(HOME_TEAM_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(HOME_TEAM_ID_REQUIRED);
    }

    @Test
    void updateMatchWillThrowExceptionWhenHomeTeamIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getHomeTeamDto().setTeamId(1);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(HOME_TEAM_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(HOME_TEAM_NON_EXISTENT);
    }

    @Test
    void updateMatchWillThrowExceptionWhenAwayTeamIdIsNull()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getAwayTeamDto().setTeamId(null);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(AWAY_TEAM_ID_REQUIRED))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(AWAY_TEAM_ID_REQUIRED);
    }

    @Test
    void updateMatchWillThrowExceptionWhenAwayTeamIdNonExistent()
            throws MissingOrNonExistentFieldException {

        expectedMatchDTO.getAwayTeamDto().setTeamId(1);

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        /* use doThrow when method returns void */
        doThrow(new MissingOrNonExistentFieldException(AWAY_TEAM_NON_EXISTENT))
                .when(fieldValidatorService)
                .verifyRequiredValues(expectedMatchDTO);

        assertThatThrownBy(() ->
                matchService.updateMatch(expectedMatchDTO))
                .isInstanceOf(MissingOrNonExistentFieldException.class)
                .hasMessageContaining(AWAY_TEAM_NON_EXISTENT);
    }

    @Test
    void canUpdateMatch() throws MissingOrNonExistentFieldException,
            FeignClientUnavailableException,
            MatchCreateException,
            ResourceIdRequiredException,
            ResourceNotFoundException {

        when(matchRepository.existsById(expectedMatch.getMatchId()))
                .thenReturn(true);

        when(matchRepository.save(expectedMatch))
                .thenReturn(expectedMatch);

        MatchDTO resultMock = new MatchDTO(1,
                new FieldDTO(2,"Staples Center","Los Angeles, California",18997),
                MLB,
                LocalDateTime.of(2023, Month.JULY, 29, 19, 30, 40),
                Collabera,
                Lakers
        );

        when(restClientService.retrieveAttributeData(expectedMatch))
                .thenReturn(resultMock);

        try (MockedStatic<MatchDtoConverter> utilities = Mockito.mockStatic(MatchDtoConverter.class)) {
            utilities.when(() -> MatchDtoConverter.toEntity(expectedMatchDTO))
                    .thenReturn(expectedMatch);
            when(MatchDtoConverter.toEntity(expectedMatchDTO)).thenReturn(expectedMatch);

            MatchDTO actual = matchService.updateMatch(expectedMatchDTO);

            assertThat(actual).isNotNull();
            assertThat(actual.getTournamentDTO()).isEqualTo(resultMock.getTournamentDTO());
            assertThat(actual.getTournamentDTO()).isNotEqualTo(expectedMatchDTO.getTournamentDTO());
            assertThat(actual.getMatchId()).isNotNull();
            assertThat(actual.getMatchId()).isEqualTo(resultMock.getMatchId());
        }

    }

    @Test
    void deleteMatchWillThrowException() {

        int negative = -1132;
        assertThatThrownBy(() -> matchService.deleteMatch(negative))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(ID_PARAM_LESS_THAN_ZERO);

        int teamId = 1;
        when(matchRepository.existsById(teamId)).thenReturn(false);

        assertThatThrownBy(() -> matchService.deleteMatch(teamId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.format(RESOURCE_NOT_FOUND,teamId));
    }

    @Test
    void deleteMatchWillThrowExceptionWhenUnsuccessful()
            throws ResourceNotFoundException {
        int teamId = 1;

        when(matchRepository.existsById(teamId))
                .thenReturn(true,true);

        String result = matchService.deleteMatch(teamId);

        verify(matchRepository).deleteById(teamId);
        assertThat(result).isEqualTo(String.format(DELETE_MESSAGE_FAILED, teamId));
    }

    @Test
    void canDeleteMatch() throws ResourceNotFoundException {
        int teamId = 1;

        when(matchRepository.existsById(teamId))
                .thenReturn(true,false);

        String result = matchService.deleteMatch(teamId);

        verify(matchRepository).deleteById(teamId);
        assertThat(result).isEqualTo(String.format(DELETE_MESSAGE_SUCCESS, teamId));
    }

}
