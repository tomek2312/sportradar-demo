package com.example.sportradardemo.domain.comparator;

import com.example.sportradardemo.domain.model.Match;
import com.example.sportradardemo.domain.model.MatchScore;
import com.example.sportradardemo.domain.repository.dto.MatchDTO;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MatchComparatorTest {
    private MatchComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new MatchComparator();
    }

    @Test
    void shouldReturnMatchesInProperOrder() {
        val match1 = createMatch(0, 5);
        val match2 = createMatch(10, 2);
        val match3 = createMatch(2, 2);
        val match4 = createMatch(6, 6);
        val match5 = createMatch(3, 1);
        val match6 = createMatch(20, 1);
        val match7 = createMatch(0, 0);
        val match8 = createMatch(0, 0);

        val list = List.of(match1, match2, match3, match4, match5, match6, match7, match8);
        val sortedList = list.stream().sorted(comparator).toList();
        sortedList.forEach(match -> System.out.println(printMatch(match)));

        assertEquals(List.of(match6, match4, match2, match1, match5, match3, match8, match7), sortedList);
    }

    private Match createMatch(int homeScore, int awayScore) {
        return new Match(UUID.randomUUID(), new MatchScore(new AtomicInteger(homeScore), new AtomicInteger(awayScore)), Instant.now(), null);
    }

    private String printMatch(Match match){
        return match.getMatchScore().getHomeTeamResult() + " - " + match.getMatchScore().getAwayTeamResult();
    }
}