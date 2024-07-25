package com.example.sportradardemo.adapter.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchRequest {
    String homeTeamName;
    String awayTeamName;
}
