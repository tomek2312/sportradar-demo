package com.example.sportradardemo.domain.repository.dto;

import lombok.Value;

@Value
public class MatchDTO {
    String id;
    String homeTeamName;
    int homeTeamResult;
    String awayTeamName;
    int awayTeamResult;
    String startedAt;
    String finishedAt;
}
