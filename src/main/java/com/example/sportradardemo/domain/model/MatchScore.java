package com.example.sportradardemo.domain.model;

import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class MatchScore {
    private final AtomicInteger homeTeamResult = new AtomicInteger();
    private final AtomicInteger awayTeamResult = new AtomicInteger();

    public int getTotalGoals() {
        return homeTeamResult.get() + awayTeamResult.get();
    }

    public void homeTeamScored() {
        homeTeamResult.incrementAndGet();
    }

    public void awayTeamScored() {
        awayTeamResult.incrementAndGet();
    }
}
