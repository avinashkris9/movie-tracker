package com.github.avinashkris9.movietracker.model;

import java.util.Map;
import lombok.Data;

@Data
public class StatisticsDTO {

  private Long movieCount;
  private Long tvCount;
  private Long movieWithMaxRating;
  private Long tvWithMaxRating;
  private Map<Integer, Long> monthlyCount;


}
