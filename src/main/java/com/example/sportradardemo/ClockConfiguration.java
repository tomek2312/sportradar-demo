package com.example.sportradardemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfiguration {
  public static final ZoneId ZONE_ID_EUROPE_WARSAW = ZoneId.of("Europe/Warsaw");

  @Bean
  public Clock clock() {
    return Clock.system(ZONE_ID_EUROPE_WARSAW);
  }
}
