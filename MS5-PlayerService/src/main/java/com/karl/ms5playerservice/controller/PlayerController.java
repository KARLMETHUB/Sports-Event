package com.karl.ms5playerservice.controller;

import com.karl.ms5playerservice.dto.PlayerDTO;
import com.karl.ms5playerservice.exception.ResourceIdShouldBeNullException;
import com.karl.ms5playerservice.exception.ResourceNotFoundException;
import com.karl.ms5playerservice.exception.ResourceIdRequiredException;
import com.karl.ms5playerservice.service.PlayerService;
import com.karl.ms5playerservice.utils.PlayerDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static com.karl.ms5playerservice.utils.PlayerDtoConverter.toDto;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/")
    public ResponseEntity<Set<PlayerDTO>> getAllPlayers() {

        return new ResponseEntity<>(playerService
                .getAllPlayers()
                .stream()
                .map(PlayerDtoConverter::toDto)
                .collect(Collectors.toSet()),
                HttpStatus.OK);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("playerId") int playerId) {

        return new ResponseEntity<>(playerService
                .getPlayersByPlayerId(playerId)
                .map(PlayerDtoConverter::toDto)
                .orElse(null)
                ,HttpStatus.OK);
    }

    @GetMapping("/searchbyteam/{teamId}")
    public ResponseEntity<Set<PlayerDTO>> getPlayersByTeamId(@PathVariable("teamId") int teamId) {

        return new ResponseEntity<>(playerService
                .getPlayersByTeamId(teamId)
                .stream()
                .map(PlayerDtoConverter::toDto)
                .collect(Collectors.toSet()),
                HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO)
            throws ResourceIdShouldBeNullException {

        return new ResponseEntity<>(
                toDto(playerService.createPlayer(playerDTO)),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO playerDTO)
            throws ResourceNotFoundException, ResourceIdRequiredException {

        return new ResponseEntity<>(
                toDto(playerService.updatePlayer(playerDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{playerId}")
    public ResponseEntity<String> deletePlayerById(@PathVariable("playerId") int playerId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                playerService.deletePlayer(playerId),
                HttpStatus.OK);
    }
}
