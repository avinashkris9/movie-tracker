package com.github.avinashkris9.movietracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class MovieDBDetails {

  @JsonProperty("results")
  List<MovieDB> movieDBDetails;


}
