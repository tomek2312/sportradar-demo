package com.example.sportradardemo.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {
    private static final Clock clock = Clock.systemUTC();
    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match("home", "away");
    }

    @Test
    void scoreShouldBeEmptyAfterStarting() {
        match.startMatch(clock);
        assertEquals(0, match.getTotalGoals());
    }

    @Test
    void shouldThrowExceptionWhenScoringBeforeMatchStarts() {
        assertThrows(IllegalStateException.class, () -> match.homeTeamScored());
        assertThrows(IllegalStateException.class, () -> match.awayTeamScored());
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterMatchEnds() {
        match.startMatch(clock);
        match.endMatch(clock);
        assertThrows(IllegalStateException.class, () -> match.homeTeamScored());
        assertThrows(IllegalStateException.class, () -> match.awayTeamScored());
    }

    @Test
    void shouldUpdateScoreAfterHomeTeamScores() {
        match.startMatch(clock);
        match.homeTeamScored();
        assertEquals(1, match.getTotalGoals());
    }

    @Test
    void shouldUpdateScoreAfterAwayTeamScores() {
        match.startMatch(clock);
        match.awayTeamScored();
        assertEquals(1, match.getTotalGoals());
    }

    @Test
    void shouldThrowExceptionWhenEndingMatchBeforeStarting() {
        assertThrows(IllegalStateException.class, () -> match.endMatch(clock));
    }
}