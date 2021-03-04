package com.github.avinashkris9.movietracker.model;

import java.util.ArrayList;
import java.util.List;

public class PageMovieResponse {


  private List<MovieResponse> movieDetails=new ArrayList<>();
  private long totalElements;
  private int totalPages;

  public PageMovieResponse(
      List<MovieResponse> movieDetails, long totalElements, int totalPages) {
    this.movieDetails = movieDetails;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }

  public PageMovieResponse() {
  }

  public List<MovieResponse> getMovieDetails() {
    return movieDetails;
  }

  public void setMovieDetails(
      List<MovieResponse> movieDetails) {
    this.movieDetails = movieDetails;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
}
