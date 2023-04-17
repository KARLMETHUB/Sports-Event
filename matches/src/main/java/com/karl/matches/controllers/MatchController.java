package com.karl.matches.controllers;

import com.karl.matches.exception.custom.ResourceNotFoundException;
import com.karl.matches.model.dto.MatchDTO;
import com.karl.matches.model.entities.Match;
import com.karl.matches.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/")
    public List<MatchDTO> getAll() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{matchId}")
    public MatchDTO getTeam(@PathVariable("matchId") int teamId) throws ResourceNotFoundException {
        return matchService.getMatch(teamId);
    }

}
