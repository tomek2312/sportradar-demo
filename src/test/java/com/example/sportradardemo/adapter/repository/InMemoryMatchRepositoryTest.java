package com.example.sportradardemo.adapter.repository;

import com.example.sportradardemo.domain.repository.dto.MatchDTO;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryMatchRepositoryTest {
    private InMemoryMatchRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMatchRepository();
    }

    @Test
    void shouldReturnEmptyOptionalWhenMatchDoesNotExist() {
        assertTrue(repository.getMatchById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void shouldReturnMatchAfterCreatingIt() {
        val match = matchDTO();
        repository.createMatch(match);
        assertEquals(match, repository.getMatchById(UUID.fromString(match.getId())).get());
    }

    @Test
    void shouldUpdateMatch() {
        val match = matchDTO();
        repository.createMatch(match);
        val updatedMatch = new MatchDTO(match.getId(), 1, 1, Instant.now().toString(), null);
        repository.updateMatch(updatedMatch);
        assertEquals(updatedMatch, repository.getMatchById(UUID.fromString(match.getId())).get());
    }

    @Test
    void shouldReturnOnlyOngoingMatches() {
        val notStartedMatch = matchDTO();
        val ongoingMatch1 = matchDTO(Instant.now(), null);
        val ongoingMatch2 = matchDTO(Instant.now(), null);
        val finishedMatch = matchDTO(Instant.now(), Instant.now());
        List.of(notStartedMatch, ongoingMatch1, ongoingMatch2, finishedMatch).forEach(repository::createMatch);

        val matches = repository.getAllOngoingMatches();
        assertEquals(2, matches.size());
        assertTrue(matches.containsAll(List.of(ongoingMatch1, ongoingMatch2)));
    }

    private MatchDTO matchDTO() {
        return matchDTO(null, null);
    }

    private MatchDTO matchDTO(Instant startedAt, Instant finishedAt) {
        return new MatchDTO(UUID.randomUUID().toString(), 0, 0,
                Optional.ofNullable(startedAt).map(Instant::toString).orElse(null),
                Optional.ofNullable(finishedAt).map(Instant::toString).orElse(null));
    }
}