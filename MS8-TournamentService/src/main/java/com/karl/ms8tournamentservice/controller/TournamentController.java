package com.karl.ms8tournamentservice.controller;

import com.karl.ms8tournamentservice.dto.TournamentDTO;
import com.karl.ms8tournamentservice.exceptions.ResourceNotFoundException;
import com.karl.ms8tournamentservice.exceptions.TournamentCreateException;
import com.karl.ms8tournamentservice.exceptions.TournamentUpdateException;
import com.karl.ms8tournamentservice.service.TournamentService;
import com.karl.ms8tournamentservice.utils.TournamentDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.karl.ms8tournamentservice.utils.TournamentDtoConverter.toDto;

@RestController
@RequestMapping("/api/v1/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        return new ResponseEntity<>(
                tournamentService
                        .getAllTournaments().stream().map(TournamentDtoConverter::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable("tournamentId") int tournamentId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                toDto(tournamentService.getTournament(tournamentId)),
                HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TournamentDTO> createTournament(@RequestBody TournamentDTO tournamentDTO)
            throws TournamentCreateException {

        return new ResponseEntity<>(
                toDto(tournamentService.createTournament(tournamentDTO)),
                HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<TournamentDTO> updateField(@RequestBody TournamentDTO tournamentDTO)
            throws TournamentUpdateException, ResourceNotFoundException {

        return new ResponseEntity<>(
                toDto(tournamentService.updateTournament(tournamentDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{tournamentId}")
    public ResponseEntity<String> deleteFieldById(@PathVariable("tournamentId") int tournamentId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                tournamentService.deleteTeam(tournamentId),
                HttpStatus.OK);
    }

}
