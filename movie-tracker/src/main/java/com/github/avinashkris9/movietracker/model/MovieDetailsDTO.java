package com.github.avinashkris9.movietracker.model;

import java.time.LocalDate;

public class MovieDetailsDTO {


  private long id;
  private String movieName;
  private int rating;
  private String review;
  private long externalId;
  private LocalDate lastWatched;
  private int numberOfWatch;
  private String overView;
  private String imdbId;
  private String posterPath;
  private String originalLanguage;

  public MovieDetailsDTO(long id, String movieName, int rating, String review, long externalId,
      LocalDate lastWatched, int numberOfWatch, String overView, String imdbId,
      String posterPath, String originalLanguage) {
    this.id = id;
    this.movieName = movieName;
    this.rating = rating;
    this.review = review;
    this.externalId = externalId;
    this.lastWatched = lastWatched;
    this.numberOfWatch = numberOfWatch;
    this.overView = overView;
    this.imdbId = imdbId;
    this.posterPath = posterPath;
    this.originalLanguage = originalLanguage;
  }

  public MovieDetailsDTO() {

  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMovieName() {
    return movieName;
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public long getExternalId() {
    return externalId;
  }

  public void setExternalId(long externalId) {
    this.externalId = externalId;
  }

  public LocalDate getLastWatched() {
    return lastWatched;
  }

  public void setLastWatched(LocalDate lastWatched) {
    this.lastWatched = lastWatched;
  }

  public int getNumberOfWatch() {
    return numberOfWatch;
  }

  public void setNumberOfWatch(int numberOfWatch) {
    this.numberOfWatch = numberOfWatch;
  }

  public String getOverView() {
    return overView;
  }

  public void setOverView(String overView) {
    this.overView = overView;
  }

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }
}
