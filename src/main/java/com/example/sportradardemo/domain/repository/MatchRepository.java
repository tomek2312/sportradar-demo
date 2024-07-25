package com.example.sportradardemo.domain.repository;

import com.example.sportradardemo.domain.repository.dto.MatchDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchRepository {
    void createMatch(MatchDTO match);

    void updateMatch(MatchDTO match);

    Optional<MatchDTO> getMatchById(UUID id);

    List<MatchDTO> getAllOngoingMatches();
}
