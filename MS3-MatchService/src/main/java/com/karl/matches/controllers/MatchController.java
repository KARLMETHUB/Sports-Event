package com.karl.matches.controllers;

import com.karl.matches.dto.MatchDTO;
import com.karl.matches.exception.*;
import com.karl.matches.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/")
    public ResponseEntity<List<MatchDTO>> getAllTeams() throws FeignClientUnavailableException {
        return new ResponseEntity<>(
                matchService.getAllMatches()
                , HttpStatus.OK);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<MatchDTO> getTeam(@PathVariable("matchId") int matchId)
            throws ResourceNotFoundException,FeignClientUnavailableException {
        return new ResponseEntity<>(
                matchService.getByMatchId(matchId),
                HttpStatus.CREATED);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MatchDTO> createTeam(@RequestBody MatchDTO matchDTO)
            throws ResourceIdShouldBeNullException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {
        return new ResponseEntity<>(
                matchService.createMatch(matchDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MatchDTO> updateTeam(@RequestBody MatchDTO matchDTO)
            throws ResourceNotFoundException,
            ResourceIdRequiredException,
            MatchCreateException,
            FeignClientUnavailableException,
            MissingOrNonExistentFieldException {

        return new ResponseEntity<>(
                matchService.updateMatch(matchDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{matchId}")
    public ResponseEntity<String> deleteTeamById(@PathVariable("matchId") int matchId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                matchService.deleteMatch(matchId),
                HttpStatus.OK);
    }

}
