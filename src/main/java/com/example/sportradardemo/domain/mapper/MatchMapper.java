package com.example.sportradardemo.domain.mapper;

import com.example.sportradardemo.domain.model.Match;
import com.example.sportradardemo.domain.model.MatchScore;
import com.example.sportradardemo.domain.repository.dto.MatchDTO;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MatchMapper implements Mapper<Match, MatchDTO>{
    @Override
    public Match mapToDomain(MatchDTO dto) {
        val id = UUID.fromString(dto.getId());
        val matchScore = new MatchScore(new AtomicInteger(dto.getHomeTeamResult()), new AtomicInteger(dto.getAwayTeamResult()));
        val matchStartedAt = parseInstant(dto.getStartedAt());
        val matchFinishedAt = parseInstant(dto.getFinishedAt());

        return new Match(id, matchScore, matchStartedAt, matchFinishedAt);
    }

    @Override
    public MatchDTO mapToDTO(Match domain) {
        return new MatchDTO(
                domain.getId().toString(),
                domain.getMatchScore().getHomeTeamResult().get(),
                domain.getMatchScore().getAwayTeamResult().get(),
                domain.getStartTime().toString(),
                domain.getEndTime().toString());
    }

    private Instant parseInstant(String instant) {
        return Optional.ofNullable(instant)
                .map(Instant::parse)
                .orElse(null);
    }
}
