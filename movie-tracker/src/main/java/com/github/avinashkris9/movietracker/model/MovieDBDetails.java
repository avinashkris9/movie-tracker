package com.github.avinashkris9.movietracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MovieDBDetails {

  @JsonProperty("results")
  List<MovieDB> movieDBDetails;

  public MovieDBDetails() {
  }

  public MovieDBDetails(List<MovieDB> movieDBDetails) {
    this.movieDBDetails = movieDBDetails;
  }

  public List<MovieDB> getMovieDBDetails() {
    return movieDBDetails;
  }

  public void setMovieDBDetails(List<MovieDB> movieDBDetails) {
    this.movieDBDetails = movieDBDetails;
  }

  @Override
  public String toString() {
    return "MovieDBDetails{" +
        "movieDBDetails=" + movieDBDetails +
        '}';
  }
}
