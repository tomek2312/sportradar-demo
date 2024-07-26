package com.example.sportradardemo.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.sportradardemo.TestClockUtils.testClock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchTest {
    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match("home", "away");
    }

    @Test
    void scoreShouldBeEmptyAfterStarting() {
        match.startMatch(testClock);
        assertEquals(0, match.getTotalGoals());
    }

    @Test
    void shouldThrowExceptionWhenScoringBeforeMatchStarts() {
        assertThrows(IllegalStateException.class, () -> match.homeTeamScored());
        assertThrows(IllegalStateException.class, () -> match.awayTeamScored());
    }

    @Test
    void shouldThrowExceptionWhenScoringAfterMatchEnds() {
        match.startMatch(testClock);
        match.endMatch(testClock);
        assertThrows(IllegalStateException.class, () -> match.homeTeamScored());
        assertThrows(IllegalStateException.class, () -> match.awayTeamScored());
    }

    @Test
    void shouldUpdateScoreAfterHomeTeamScores() {
        match.startMatch(testClock);
        match.homeTeamScored();
        assertEquals(1, match.getTotalGoals());
    }

    @Test
    void shouldUpdateScoreAfterAwayTeamScores() {
        match.startMatch(testClock);
        match.awayTeamScored();
        assertEquals(1, match.getTotalGoals());
    }

    @Test
    void shouldThrowExceptionWhenEndingMatchBeforeStarting() {
        assertThrows(IllegalStateException.class, () -> match.endMatch(testClock));
    }
}