package com.example.sportradardemo.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class Match implements Comparable<Match> {
    @Getter
    private final UUID id = UUID.randomUUID();
    private final AtomicInteger homeTeamResult = new AtomicInteger();
    private final AtomicInteger awayTeamResult = new AtomicInteger();
    private Instant startTime;
    private Instant endTime;

    public int getTotalGoals() {
        return homeTeamResult.get() + awayTeamResult.get();
    }

    public int homeTeamScored() {
        checkIfMatchIsOngoing();
        return homeTeamResult.incrementAndGet();
    }

    public int awayTeamScored() {
        checkIfMatchIsOngoing();
        return awayTeamResult.incrementAndGet();
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
    public int compareTo(Match o) {
        if (this.getTotalGoals() == o.getTotalGoals()) {
            return this.startTime.compareTo(o.startTime);
        }

        return this.getTotalGoals() - o.getTotalGoals();
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
