package com.example.sportradardemo.domain.comparator;

import com.example.sportradardemo.domain.model.Match;
import com.example.sportradardemo.domain.model.Team;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.sportradardemo.TestClockUtils.testClock;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchComparatorTest {
    private MatchComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new MatchComparator();
    }

    @Test
    void shouldNotChangeOrderAfterFinishingMatch(){
        val match1 = createStartedMatch(0, 0);
        val match2 = createStartedMatch(1, 0);
        val match3 = createStartedMatch(2, 0);

        val list = Arrays.asList(match1, match2, match3);
        list.sort(comparator);

        val expectedList = List.of(match3, match2, match1);
        assertEquals(expectedList, list);

        match3.endMatch(testClock);

        val changedList = Arrays.asList(match1, match2, match3);
        changedList.sort(comparator);

        assertEquals(expectedList, changedList);
    }

    @Test
    void shouldReturnMatchesInProperOrder() {
        val match1 = createStartedMatch(0, 5);
        val match2 = createStartedMatch(10, 2);
        val match3 = createStartedMatch(2, 2);
        val match4 = createStartedMatch(6, 6);
        val match5 = createStartedMatch(3, 1);
        val match6 = createStartedMatch(20, 1);
        val match7 = createStartedMatch(0, 0);
        val match8 = createStartedMatch(0, 0);

        val list = Arrays.asList(match1, match2, match3, match4, match5, match6, match7, match8);
        list.sort(comparator);

        val expectedList = List.of(match6, match4, match2, match1, match5, match3, match8, match7);
        assertEquals(expectedList, list);
    }

    private Match createStartedMatch(int homeScore, int awayScore) {
        return new Match(UUID.randomUUID(),
                new Team(new AtomicInteger(homeScore), "home"),
                new Team(new AtomicInteger(awayScore), "away"),
                Instant.now(), null);
    }
}