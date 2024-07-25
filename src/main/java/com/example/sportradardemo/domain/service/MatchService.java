package com.example.sportradardemo.domain.service;

import com.example.sportradardemo.domain.model.Match;

import java.util.List;
import java.util.UUID;

public interface MatchService {
    Match createMatch(String homeTeamName, String awayTeamName);

    void homeTeamScored(UUID matchId);

    void awayTeamScored(UUID matchId);

    void finishMatch(UUID matchId);

    List<Match> getScoreboard();
}
