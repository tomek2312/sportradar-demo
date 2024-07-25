package com.example.sportradardemo.adapter.repository;

import com.example.sportradardemo.domain.repository.MatchRepository;
import com.example.sportradardemo.domain.repository.dto.MatchDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InMemoryMatchRepository implements MatchRepository {
    private final Map<UUID, MatchDTO> matches;

    public InMemoryMatchRepository() {
        this.matches = new HashMap<>();
    }

    @Override
    public void createMatch(MatchDTO match) {
        matches.put(UUID.fromString(match.getId()), match);
    }

    @Override
    public void updateMatch(MatchDTO match) {
        matches.put(UUID.fromString(match.getId()), match);
    }

    @Override
    public Optional<MatchDTO> getMatchById(UUID id) {
        return Optional.ofNullable(matches.get(id));
    }

    @Override
    public List<MatchDTO> getAllOngoingMatches() {
        return matches.keySet()
                .stream()
                .map(matches::get)
                .filter(matchDTO -> matchDTO.getStartedAt() != null)
                .filter(matchDTO -> matchDTO.getFinishedAt() == null)
                .toList();
    }
}
