package com.example.sportradardemo.adapter.controller;

import com.example.sportradardemo.adapter.controller.model.CreateMatchRequest;
import com.example.sportradardemo.domain.model.Match;
import com.example.sportradardemo.domain.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
@AllArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    Match createMatch(@RequestBody CreateMatchRequest request) {
        return matchService.createMatch(request.getHomeTeamName(), request.getAwayTeamName());
    }

    @PostMapping("/{uuid}/homeTeamScored")
    void homeTeamScored(@PathVariable UUID uuid) {
        matchService.homeTeamScored(uuid);
    }

    @PostMapping("/{uuid}/awayTeamScored")
    void awayTeamScored(@PathVariable UUID uuid) {
        matchService.awayTeamScored(uuid);
    }

    @PostMapping("/{uuid}/finishMatch")
    void finishMatch(@PathVariable UUID uuid) {
        matchService.finishMatch(uuid);
    }

    @GetMapping("/scoreboard")
    List<Match> getScoreboard() {
        return matchService.getScoreboard();
    }
}
