package com.example.sportradardemo.domain.service;

import com.example.sportradardemo.TestClockUtils;
import com.example.sportradardemo.domain.comparator.MatchComparator;
import com.example.sportradardemo.domain.mapper.MatchMapper;
import com.example.sportradardemo.domain.repository.MatchRepository;
import com.example.sportradardemo.domain.repository.dto.MatchDTO;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {
    @Mock
    private MatchRepository matchRepository;

    @Spy
    private MatchMapper matchMapper = new MatchMapper();

    @Spy
    private MatchComparator matchComparator = new MatchComparator();

    @Spy
    private Clock clock = TestClockUtils.testClock;

    @InjectMocks
    private MatchServiceImpl matchService;

    @Test
    void shouldCreateMatch() {
        val homeTeamName = "Home Team";
        val awayTeamName = "Away Team";

        val resultMatch = matchService.createMatch(homeTeamName, awayTeamName);

        assertNotNull(resultMatch);
        assertEquals(homeTeamName, resultMatch.getHomeTeam().getName());
        assertEquals(awayTeamName, resultMatch.getAwayTeam().getName());
        assertNotNull(resultMatch.getStartTime());

        verify(matchRepository).createMatch(any());
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void shouldThrowExceptionWhenMatchNotFoundWhileHomeTeamScored(){
        val matchId = UUID.randomUUID();
        when(matchRepository.getMatchById(matchId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> matchService.homeTeamScored(matchId));

        verify(matchRepository).getMatchById(matchId);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void shouldUpdateMatchWhenHomeTeamScores(){
        val existingMatch = startedMatchDTO();
        val existingMatchId = UUID.fromString(existingMatch.getId());
        when(matchRepository.getMatchById(existingMatchId))
                .thenReturn(Optional.of(existingMatch));

        matchService.homeTeamScored(existingMatchId);

        verify(matchRepository).getMatchById(existingMatchId);
        verify(matchRepository).updateMatch(any());
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void shouldThrowExceptionWhenMatchNotFoundWhileAwayTeamScored(){
        val matchId = UUID.randomUUID();
        when(matchRepository.getMatchById(matchId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> matchService.awayTeamScored(matchId));

        verify(matchRepository).getMatchById(matchId);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void shouldUpdateMatchWhenAwayTeamScores(){
        val existingMatch = startedMatchDTO();
        val existingMatchId = UUID.fromString(existingMatch.getId());
        when(matchRepository.getMatchById(existingMatchId))
                .thenReturn(Optional.of(existingMatch));

        matchService.awayTeamScored(existingMatchId);

        verify(matchRepository).getMatchById(existingMatchId);
        verify(matchRepository).updateMatch(any());
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void shouldThrowExceptionWhenMatchNotFoundWhileEndingMatch(){
        val matchId = UUID.randomUUID();
        when(matchRepository.getMatchById(matchId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> matchService.finishMatch(matchId));

        verify(matchRepository).getMatchById(matchId);
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void shouldUpdateMatchWhenMatchEnds(){
        val existingMatch = startedMatchDTO();
        val existingMatchId = UUID.fromString(existingMatch.getId());
        when(matchRepository.getMatchById(existingMatchId))
                .thenReturn(Optional.of(existingMatch));

        matchService.finishMatch(existingMatchId);

        verify(matchRepository).getMatchById(existingMatchId);
        verify(matchRepository).updateMatch(any());
        verifyNoMoreInteractions(matchRepository);
    }

    @Test
    void shouldReturnScoreboard(){
        val ongoingMatches = List.of(startedMatchDTO(), startedMatchDTO(), startedMatchDTO());
        when(matchRepository.getAllOngoingMatches())
                .thenReturn(ongoingMatches);

        val scoreboardResult = matchService.getScoreboard();

        assertEquals(ongoingMatches.size(), scoreboardResult.size());
        verify(matchRepository).getAllOngoingMatches();
        verifyNoMoreInteractions(matchRepository);
    }

    private MatchDTO startedMatchDTO(){
        return new MatchDTO(UUID.randomUUID().toString(), "Home Team", 0,
                "Away Team",0,
                Instant.now().toString(), null);
    }
}