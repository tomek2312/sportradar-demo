package com.example.sportradardemo.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreTest {
    private MatchScore matchScore;

    @BeforeEach
    void setUp() {
        matchScore = new MatchScore();
    }

    @Test
    void scoreShouldBeEmptyAfterStarting() {
        assertEquals(0, matchScore.getTotalGoals());
    }

    @Test
    void shouldUpdateScoreAfterHomeTeamScores() {
        matchScore.homeTeamScored();
        assertEquals(1, matchScore.getTotalGoals());
    }

    @Test
    void shouldUpdateScoreAfterAwayTeamScores() {
        matchScore.awayTeamScored();
        assertEquals(1, matchScore.getTotalGoals());
    }
}