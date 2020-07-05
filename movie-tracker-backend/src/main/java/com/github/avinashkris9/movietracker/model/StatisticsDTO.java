package com.github.avinashkris9.movietracker.model;

import lombok.Data;

@Data
public class StatisticsDTO {

  private long movieCount;
  private long tvCount;
  private long movieWithMaxRating;
  private long tvWithMaxRating;


}
