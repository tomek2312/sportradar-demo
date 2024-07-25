package com.example.sportradardemo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Match {
    private final UUID id;
    private final MatchScore matchScore;
    private Instant startTime;
    private Instant endTime;

    public Match() {
        this.id = UUID.randomUUID();
        this.matchScore = new MatchScore();
    }

    public int getTotalGoals() {
        return matchScore.getTotalGoals();
    }

    public void homeTeamScored() {
        checkIfMatchIsOngoing();
        matchScore.homeTeamScored();
    }

    public void awayTeamScored() {
        checkIfMatchIsOngoing();
        matchScore.awayTeamScored();
    }

    public void startMatch(@NonNull Clock clock) {
        if (isMatchStarted()) {
            throw new IllegalStateException("Match already started");
        }

        startTime = clock.instant();
    }

    public void endMatch(@NonNull Clock clock) {
        checkIfMatchIsOngoing();
        endTime = clock.instant();
    }

    private boolean isMatchStarted() {
        return startTime != null;
    }

    private boolean isMatchFinished() {
        return endTime != null;
    }

    private void checkIfMatchIsOngoing() {
        if (!isMatchStarted()) {
            throw new IllegalStateException("Match not started yet");
        }

        if (isMatchFinished()) {
            throw new IllegalStateException("Match already ended");
        }
    }
}
