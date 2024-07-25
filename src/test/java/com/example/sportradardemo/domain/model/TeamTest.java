package com.example.sportradardemo.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamTest {
    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("Poland");
    }

    @Test
    void scoreShouldBeEmptyAfterStarting() {
        assertEquals(0, team.getScore());
    }

    @Test
    void shouldUpdateScoreAfterGoal() {
        team.scoreGoal();
        assertEquals(1, team.getScore());
    }
}