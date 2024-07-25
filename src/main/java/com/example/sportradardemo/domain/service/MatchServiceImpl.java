package com.example.sportradardemo.domain.service;

import com.example.sportradardemo.domain.comparator.MatchComparator;
import com.example.sportradardemo.domain.mapper.MatchMapper;
import com.example.sportradardemo.domain.model.Match;
import com.example.sportradardemo.domain.repository.MatchRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchMapper matchMapper;
    private final MatchComparator matchComparator;
    private final MatchRepository matchRepository;
    private final Clock clock;

    @Override
    public Match createMatch(String homeTeamName, String awayTeamName) {
        val match = new Match(homeTeamName, awayTeamName);
        match.startMatch(clock);
        matchRepository.createMatch(matchMapper.mapToDTO(match));

        return match;
    }

    @Override
    public void homeTeamScored(UUID matchId) {
        val match = matchRepository.getMatchById(matchId)
                .map(matchMapper::mapToDomain)
                .orElseThrow(() -> new NoSuchElementException("Match not found"));

        match.homeTeamScored();
        matchRepository.updateMatch(matchMapper.mapToDTO(match));
    }

    @Override
    public void awayTeamScored(UUID matchId) {
        val match = matchRepository.getMatchById(matchId)
                .map(matchMapper::mapToDomain)
                .orElseThrow(() -> new NoSuchElementException("Match not found"));

        match.awayTeamScored();
        matchRepository.updateMatch(matchMapper.mapToDTO(match));
    }

    @Override
    public void finishMatch(UUID matchId) {
        val match = matchRepository.getMatchById(matchId)
                .map(matchMapper::mapToDomain)
                .orElseThrow(() -> new NoSuchElementException("Match not found"));

        match.endMatch(clock);
        matchRepository.updateMatch(matchMapper.mapToDTO(match));
    }

    @Override
    public List<Match> getScoreboard() {
        return matchRepository.getAllOngoingMatches()
                .stream()
                .map(matchMapper::mapToDomain)
                .sorted(matchComparator)
                .toList();
    }
}
