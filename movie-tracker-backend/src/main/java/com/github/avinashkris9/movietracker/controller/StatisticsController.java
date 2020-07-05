package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.model.StatisticsDTO;
import com.github.avinashkris9.movietracker.service.MovieService;
import com.github.avinashkris9.movietracker.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin
@Slf4j
public class StatisticsController {


  private final StatisticsService statisticsService;

  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @GetMapping
  public StatisticsDTO getStatistics() {
    return statisticsService.produceStatisticsData();

  }


}
