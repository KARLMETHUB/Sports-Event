package com.karl.matches.repo;

import com.karl.matches.model.dto.MatchDTO;
import com.karl.matches.model.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match,Integer> {
    Optional<Match> findOneByMatchId(int matchId);
}
