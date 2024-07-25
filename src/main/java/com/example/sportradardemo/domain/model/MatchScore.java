package com.example.sportradardemo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Getter
public class MatchScore {
    private final AtomicInteger homeTeamResult;
    private final AtomicInteger awayTeamResult;

    public MatchScore(){
        this.homeTeamResult = new AtomicInteger();
        this.awayTeamResult = new AtomicInteger();
    }

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
