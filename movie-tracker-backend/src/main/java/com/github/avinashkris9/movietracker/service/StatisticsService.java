package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.model.StatisticsDTO;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides statistical table data.
 */
@Service
@Slf4j
public class StatisticsService {


  private final MovieService movieService;
  private final TvService tvService;
  @Autowired
  public StatisticsService(
      MovieService movieService,
      TvService tvService) {
    this.movieService = movieService;
    this.tvService = tvService;
  }


  public StatisticsDTO produceStatisticsData()
  {
    StatisticsDTO statisticsDTO=new StatisticsDTO();
    statisticsDTO.setMovieCount(movieService.getMovieCount());
    statisticsDTO.setMovieWithMaxRating(movieService.getTopRatedMovieCount());
    statisticsDTO.setTvCount(tvService.getTvCount());
    statisticsDTO.setTvWithMaxRating(tvService.getTopRatedTvCount());

    /**
     * @TODO Broken !!!!!!!!!!!!
     * the query is not correct and data is not correct.
     */
 Map<Integer,Long> monthlyMovieCount=movieService.getMonthlyCount();;
    statisticsDTO.setMonthlyCount(monthlyMovieCount);
    log.debug(" Statistics Data {}",statisticsDTO);
    return statisticsDTO;
  }

}
