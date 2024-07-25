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
    private final Team homeTeam;
    private final Team awayTeam;
    private Instant startTime;
    private Instant endTime;

    public Match(String homeTeamName, String awayTeamName) {
        this.id = UUID.randomUUID();
        this.homeTeam = new Team(homeTeamName);
        this.awayTeam = new Team(awayTeamName);
    }

    public int getTotalGoals() {
        return homeTeam.getScore() + awayTeam.getScore();
    }

    public void homeTeamScored() {
        checkIfMatchIsOngoing();
        homeTeam.scoreGoal();
    }

    public void awayTeamScored() {
        checkIfMatchIsOngoing();
        awayTeam.scoreGoal();
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

    @Override
    public String toString() {
        return String.join(" - ", homeTeam.toString(), awayTeam.toString());
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
