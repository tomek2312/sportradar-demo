package com.example.sportradardemo.domain.repository.dto;

import lombok.Value;

@Value
public class MatchDTO {
    String id;
    int homeTeamResult;
    int awayTeamResult;
    String startedAt;
    String finishedAt;
}
