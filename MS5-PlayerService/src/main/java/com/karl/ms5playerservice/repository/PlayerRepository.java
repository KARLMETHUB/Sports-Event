package com.karl.ms5playerservice.repository;

import com.karl.ms5playerservice.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    Set<Player> findAllByTeamId(Integer teamId);

}
