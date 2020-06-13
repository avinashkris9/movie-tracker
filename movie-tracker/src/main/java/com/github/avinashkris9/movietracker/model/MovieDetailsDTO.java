package com.github.avinashkris9.movietracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class MovieDetailsDTO {

  private long id;
  private String movieName;
  private int rating;
  private String review;
  private long externalId;

  @JsonSerialize(as = LocalDate.class)
  @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
  private LocalDate lastWatched;

  private int numberOfWatch;

  @JsonProperty(access = Access.READ_ONLY)
  private String overView;

  @JsonProperty(access = Access.READ_ONLY)
  private String imdbId;

  @JsonProperty(access = Access.READ_ONLY)
  private String posterPath;

  @JsonProperty(access = Access.READ_ONLY)
  private String originalLanguage;

  public MovieDetailsDTO(
      long id,
      String movieName,
      int rating,
      String review,
      long externalId,
      LocalDate lastWatched,
      int numberOfWatch,
      String overView,
      String imdbId,
      String posterPath,
      String originalLanguage) {
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

  public MovieDetailsDTO() {}

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

  @Override
  public String toString() {
    return "MovieDetailsDTO{"
        + "id="
        + id
        + ", movieName='"
        + movieName
        + '\''
        + ", rating="
        + rating
        + ", review='"
        + review
        + '\''
        + ", externalId="
        + externalId
        + ", lastWatched="
        + lastWatched
        + ", numberOfWatch="
        + numberOfWatch
        + ", overView='"
        + overView
        + '\''
        + ", imdbId='"
        + imdbId
        + '\''
        + ", posterPath='"
        + posterPath
        + '\''
        + ", originalLanguage='"
        + originalLanguage
        + '\''
        + '}';
  }
}
