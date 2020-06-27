package com.github.avinashkris9.movietracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MovieDB {

  @JsonProperty("id")
  private long movieId;

  @JsonProperty("overview")
  private String movieSummary;

  @JsonProperty("imdb_id")
  private String imdbId;

  private String title;

  private String name;
  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("original_language")
  private String originalLanguge;


}
