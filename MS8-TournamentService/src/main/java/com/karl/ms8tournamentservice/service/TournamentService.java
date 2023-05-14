package com.karl.ms8tournamentservice.service;

import com.karl.ms8tournamentservice.dto.TournamentDTO;
import com.karl.ms8tournamentservice.entities.Tournament;
import com.karl.ms8tournamentservice.exceptions.*;
import com.karl.ms8tournamentservice.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.karl.ms8tournamentservice.constants.ExceptionMessage.*;
import static com.karl.ms8tournamentservice.constants.ResponseMessage.*;
import static com.karl.ms8tournamentservice.utils.TournamentDtoConverter.toEntity;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getTournament(int tournamentId) throws ResourceNotFoundException {
        Optional<Tournament> field = tournamentRepository.findById(tournamentId);

        return field.orElseThrow(() ->
                new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,tournamentId)));
    }


    public Tournament createTournament(TournamentDTO tournamentDTO) throws TournamentCreateException {

        if (tournamentDTO.getTournamentId() != null)
            throw new TournamentCreateException(ID_SHOULD_BE_NULL_ON_CREATE);

        return tournamentRepository.save(toEntity(tournamentDTO));
    }

    public Tournament updateTournament(TournamentDTO tournamentDTO) throws TournamentUpdateException, ResourceNotFoundException {

        if (tournamentDTO.getTournamentId() == null)
            throw new TournamentUpdateException(ID_REQUIRED);

        if (!tournamentRepository.existsById(tournamentDTO.getTournamentId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,tournamentDTO.getTournamentId()));

        return tournamentRepository.save(toEntity(tournamentDTO));
    }

    public String deleteTeam(int tournamentId) throws ResourceNotFoundException {
        if (tournamentId < 1)
            throw new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO);

        if(!tournamentRepository.existsById(tournamentId))
            throw new ResourceNotFoundException(
                    String.format(RESOURCE_NOT_FOUND,tournamentId));

        tournamentRepository.deleteById(tournamentId);

        return String.format(tournamentRepository.existsById(tournamentId) ?
                DELETE_MESSAGE_FAILED : DELETE_MESSAGE_SUCCESS, tournamentId);
    }

}
