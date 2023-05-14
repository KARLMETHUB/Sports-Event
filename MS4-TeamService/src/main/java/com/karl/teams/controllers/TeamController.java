package com.karl.teams.controllers;

import com.karl.teams.exceptions.PlayerAssignmentException;
import com.karl.teams.exceptions.ResourceIdRequiredException;
import com.karl.teams.exceptions.ResourceIdShouldBeNullException;
import com.karl.teams.exceptions.ResourceNotFoundException;
import com.karl.teams.model.dto.PlayerDTO;
import com.karl.teams.model.dto.TeamDTO;
import com.karl.teams.service.TeamService;
import com.karl.teams.utils.TeamDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.karl.teams.constants.LogMessage.*;
import static com.karl.teams.utils.TeamDtoConverter.toDto;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private static final Logger log = Logger.getLogger(TeamController.class.getName());
    private final TeamService teamService;
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        log.info(RETRIEVE_ALL_TEAMS);
        return new ResponseEntity<>(
                teamService
                .getAllTeams()
                .stream()
                .map(TeamDtoConverter::toDto)
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    // TODO: 5/14/2023 : return Response entity
    @GetMapping("/{teamId}")
    public TeamDTO getTeam(@PathVariable("teamId") int teamId) throws ResourceNotFoundException {
        return toDto(teamService.getTeamById(teamId));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO)
            throws ResourceIdShouldBeNullException {
        return new ResponseEntity<>(
                toDto(teamService.createTeam(teamDTO)),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TeamDTO> updateTeam(@RequestBody TeamDTO teamDTO)
            throws ResourceNotFoundException, ResourceIdRequiredException {

        return new ResponseEntity<>(
                toDto(teamService.updateTeam(teamDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{teamId}")
    public ResponseEntity<String> deleteTeamById(@PathVariable("teamId") int teamId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                teamService.deleteTeam(teamId),
                HttpStatus.OK);
    }

    @PutMapping("/assignPlayer")
    public ResponseEntity<String> updateTeamPlayers(@RequestBody PlayerDTO playerDTO)
            throws PlayerAssignmentException {

        return new ResponseEntity<>(
                teamService.assignPlayerToTeam(playerDTO),
                HttpStatus.OK);
    }

    @PutMapping("/removePlayer")
    public ResponseEntity<String> removeTeamPlayers(@RequestBody PlayerDTO playerDTO)
            throws PlayerAssignmentException {

        return new ResponseEntity<>(
                teamService.removePlayerToTeam(playerDTO),
                HttpStatus.OK);
    }

}
