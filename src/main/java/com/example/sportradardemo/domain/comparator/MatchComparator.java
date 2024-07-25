package com.example.sportradardemo.domain.comparator;

import com.example.sportradardemo.domain.model.Match;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;

/*
 * Get a summary of games in progress ordered by their total score. The games with the same
 * total score will be returned ordered by the most recently started match in the scoreboard.
 *
 * For example, if following matches are started in the specified order and their scores
 * respectively updated:
 *
 * a. Mexico 0 - Canada 5
 * b. Spain 10 - Brazil 2
 * c. Germany 2 - France 2
 * d. Uruguay 6 - Italy 6
 * e. Argentina 3 - Australia 1
 *
 * The summary should be as follows:
 * 1. Uruguay 6 - Italy 6
 * 2. Spain 10 - Brazil 2
 * 3. Mexico 0 - Canada 5
 * 4. Argentina 3 - Australia 1
 * 5. Germany 2 - France 2
 */
@Component
public class MatchComparator implements Comparator<Match> {
    @Override
    public int compare(Match o1, Match o2) {
        if(o1.getTotalGoals() == o2.getTotalGoals())
            return getMatchStartTime(o2).compareTo(getMatchStartTime(o1));
        return o2.getTotalGoals() - o1.getTotalGoals();
    }

    private Instant getMatchStartTime(Match match) {
        return Optional.ofNullable(match.getStartTime()).orElse(Instant.MIN);
    }
}
