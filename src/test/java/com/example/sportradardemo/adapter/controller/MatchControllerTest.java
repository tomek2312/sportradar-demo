package com.example.sportradardemo.adapter.controller;

import com.example.sportradardemo.adapter.controller.model.CreateMatchRequest;
import com.example.sportradardemo.domain.model.Match;
import com.example.sportradardemo.domain.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
class MatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    class CreateMatch {
        @SneakyThrows
        @Test
        void shouldCreateMatch() {
            val request = new CreateMatchRequest("Home Team", "Away Team");
            val match = new Match(request.getHomeTeamName(), request.getAwayTeamName());

            when(matchService.createMatch(request.getHomeTeamName(), request.getAwayTeamName())).thenReturn(match);

            mockMvc.perform(post("/matches/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(match)));

            verify(matchService).createMatch(request.getHomeTeamName(), request.getAwayTeamName());
            verifyNoMoreInteractions(matchService);
        }
    }

    @Nested
    class HomeTeamScores {
        @SneakyThrows
        @Test
        void shouldUpdateMatchWhenHomeTeamScores() {
            val matchId = UUID.randomUUID();

            doNothing().when(matchService).homeTeamScored(matchId);

            mockMvc.perform(post("/matches/%s/homeTeamScored".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));

            verify(matchService).homeTeamScored(matchId);
            verifyNoMoreInteractions(matchService);
        }

        @SneakyThrows
        @Test
        void shouldGetNotFoundCodeWhenMatchNotFoundWhileHomeTeamScores() {
            val matchId = UUID.randomUUID();
            val message = "Match not found";

            doThrow(new NoSuchElementException(message)).when(matchService).homeTeamScored(matchId);

            mockMvc.perform(post("/matches/%s/homeTeamScored".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(message));

            verify(matchService).homeTeamScored(matchId);
            verifyNoMoreInteractions(matchService);
        }

        @SneakyThrows
        @Test
        void shouldGetBadRequestCodeWhenMatchNotValidWhileHomeTeamScores() {
            val matchId = UUID.randomUUID();
            val message = "Match not started";

            doThrow(new IllegalStateException(message)).when(matchService).homeTeamScored(matchId);

            mockMvc.perform(post("/matches/%s/homeTeamScored".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(message));

            verify(matchService).homeTeamScored(matchId);
            verifyNoMoreInteractions(matchService);
        }
    }

    @Nested
    class AwayTeamScores {
        @SneakyThrows
        @Test
        void shouldUpdateMatchWhenAwayTeamScores() {
            val matchId = UUID.randomUUID();

            doNothing().when(matchService).awayTeamScored(matchId);

            mockMvc.perform(post("/matches/%s/awayTeamScored".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));

            verify(matchService).awayTeamScored(matchId);
            verifyNoMoreInteractions(matchService);
        }

        @SneakyThrows
        @Test
        void shouldGetNotFoundCodeWhenMatchNotFoundWhileAwayTeamScores() {
            val matchId = UUID.randomUUID();
            val message = "Match not found";

            doThrow(new NoSuchElementException(message)).when(matchService).awayTeamScored(matchId);

            mockMvc.perform(post("/matches/%s/awayTeamScored".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(message));

            verify(matchService).awayTeamScored(matchId);
            verifyNoMoreInteractions(matchService);
        }

        @SneakyThrows
        @Test
        void shouldGetBadRequestCodeWhenMatchNotValidWhileAwayTeamScores() {
            val matchId = UUID.randomUUID();
            val message = "Match not started";

            doThrow(new IllegalStateException(message)).when(matchService).awayTeamScored(matchId);

            mockMvc.perform(post("/matches/%s/awayTeamScored".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(message));

            verify(matchService).awayTeamScored(matchId);
            verifyNoMoreInteractions(matchService);
        }
    }

    @Nested
    class FinishMatch {
        @SneakyThrows
        @Test
        void shouldFinishMatch() {
            val matchId = UUID.randomUUID();

            doNothing().when(matchService).finishMatch(matchId);

            mockMvc.perform(post("/matches/%s/finishMatch".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));

            verify(matchService).finishMatch(matchId);
            verifyNoMoreInteractions(matchService);
        }

        @SneakyThrows
        @Test
        void shouldGetNotFoundCodeWhenMatchNotFoundWhileFinishingMatch() {
            val matchId = UUID.randomUUID();
            val message = "Match not found";

            doThrow(new NoSuchElementException(message)).when(matchService).finishMatch(matchId);

            mockMvc.perform(post("/matches/%s/finishMatch".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(message));

            verify(matchService).finishMatch(matchId);
            verifyNoMoreInteractions(matchService);
        }

        @SneakyThrows
        @Test
        void shouldGetBadRequestCodeWhenMatchNotValidWhileFinishingMatch() {
            val matchId = UUID.randomUUID();
            val message = "Match not started";

            doThrow(new IllegalStateException(message)).when(matchService).finishMatch(matchId);

            mockMvc.perform(post("/matches/%s/finishMatch".formatted(matchId)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(message));

            verify(matchService).finishMatch(matchId);
            verifyNoMoreInteractions(matchService);
        }
    }

    @Nested
    class Scoreboard {
        @SneakyThrows
        @Test
        void shouldReturnScoreboard() {
            val match = new Match("home", "away");
            val scoreboard = List.of(match);

            when(matchService.getScoreboard()).thenReturn(scoreboard);

            mockMvc.perform(get("/matches/scoreboard"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(scoreboard)));

            verify(matchService).getScoreboard();
            verifyNoMoreInteractions(matchService);
        }
    }
}