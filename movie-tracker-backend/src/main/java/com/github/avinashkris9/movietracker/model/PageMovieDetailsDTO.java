package com.github.avinashkris9.movietracker.model;

import java.util.ArrayList;
import java.util.List;

public class PageMovieDetailsDTO {


  private List<MovieDetailsDTO> movieDetails=new ArrayList<>();
  private long totalElements;
  private int totalPages;

  public PageMovieDetailsDTO(
      List<MovieDetailsDTO> movieDetails, long totalElements, int totalPages) {
    this.movieDetails = movieDetails;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }

  public PageMovieDetailsDTO() {
  }

  public List<MovieDetailsDTO> getMovieDetails() {
    return movieDetails;
  }

  public void setMovieDetails(
      List<MovieDetailsDTO> movieDetails) {
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
