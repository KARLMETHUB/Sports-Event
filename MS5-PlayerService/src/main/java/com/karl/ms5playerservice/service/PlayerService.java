package com.karl.ms5playerservice.service;

import com.karl.ms5playerservice.dto.PlayerDTO;
import com.karl.ms5playerservice.entity.Player;
import com.karl.ms5playerservice.exception.ResourceIdShouldBeNullException;
import com.karl.ms5playerservice.exception.ResourceNotFoundException;
import com.karl.ms5playerservice.exception.ResourceIdRequiredException;
import com.karl.ms5playerservice.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.karl.ms5playerservice.constant.ExceptionMessage.*;
import static com.karl.ms5playerservice.constant.ResponseMessage.*;
import static com.karl.ms5playerservice.utils.PlayerDtoConverter.toEntity;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }



    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayersByPlayerId(int playerId) {

        return playerRepository.findById(playerId);
    }

    public Set<Player> getPlayersByTeamId(Integer teamId) {

        return playerRepository
                .findAllByTeamId(teamId);
    }

    public Player createPlayer(PlayerDTO playerDTO) throws ResourceIdShouldBeNullException {

        if (playerDTO.getPlayerId() != null)
            throw new ResourceIdShouldBeNullException(ID_SHOULD_BE_NULL_ON_CREATE);

        return playerRepository.save(toEntity(playerDTO));
    }
    public Player updatePlayer(PlayerDTO playerDTO) throws ResourceNotFoundException, ResourceIdRequiredException {

        if (playerDTO.getPlayerId() == null)
            throw new ResourceIdRequiredException(ID_REQUIRED);

        if (!playerRepository.existsById(playerDTO.getPlayerId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,playerDTO.getPlayerId()));

        return playerRepository.save(toEntity(playerDTO));
    }

    public String deletePlayer(Integer playerId) throws ResourceNotFoundException {

        if (playerId < 1)
            throw new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO);

        if(!playerRepository.existsById(playerId))
            throw new ResourceNotFoundException(
                    String.format(RESOURCE_NOT_FOUND,playerId));

        playerRepository.deleteById(playerId);

        return String.format(playerRepository.existsById(playerId)  ? DELETE_MESSAGE_FAILED : DELETE_MESSAGE_SUCCESS, playerId);
    }

}
