package com.example.sportradardemo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class Team {
    private final AtomicInteger result;
    @Getter
    private final String name;

    public Team(String name) {
        this.result = new AtomicInteger();
        this.name = name;
    }

    public void scoreGoal() {
        result.incrementAndGet();
    }

    public int getScore() {
        return result.get();
    }

    @Override
    public String toString() {
        return String.join(" ", name, result.toString());
    }
}
