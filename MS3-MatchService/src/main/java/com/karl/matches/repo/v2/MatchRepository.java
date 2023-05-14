package com.karl.matches.repo.v2;

import com.karl.matches.entity.v2.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match,Integer> { }

