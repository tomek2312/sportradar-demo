package com.example.sportradardemo.domain.mapper;

import com.example.sportradardemo.domain.model.Match;
import com.example.sportradardemo.domain.model.Team;
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
        val homeTeam = new Team(new AtomicInteger(dto.getHomeTeamResult()), dto.getHomeTeamName());
        val awayTeam = new Team(new AtomicInteger(dto.getAwayTeamResult()), dto.getAwayTeamName());
        val matchStartedAt = parseInstant(dto.getStartedAt());
        val matchFinishedAt = parseInstant(dto.getFinishedAt());

        return new Match(id, homeTeam, awayTeam, matchStartedAt, matchFinishedAt);
    }

    @Override
    public MatchDTO mapToDTO(Match domain) {
        return new MatchDTO(
                domain.getId().toString(),
                domain.getHomeTeam().getName(),
                domain.getHomeTeam().getScore(),
                domain.getAwayTeam().getName(),
                domain.getAwayTeam().getScore(),
                instantToString(domain.getStartTime()),
                instantToString(domain.getEndTime()));
    }

    private String instantToString(Instant instant) {
        return Optional.ofNullable(instant)
                .map(Instant::toString)
                .orElse(null);
    }

    private Instant parseInstant(String instant) {
        return Optional.ofNullable(instant)
                .map(Instant::parse)
                .orElse(null);
    }
}
