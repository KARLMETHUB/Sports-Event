package com.karl.teams.repository;

import com.karl.teams.model.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamsRepository extends JpaRepository<Team,Integer> {
    @Override
    Optional<Team> findById(Integer teamId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Team t WHERE t.teamName = ?1")
    Boolean selectExistsByTeamName(String name);

}
