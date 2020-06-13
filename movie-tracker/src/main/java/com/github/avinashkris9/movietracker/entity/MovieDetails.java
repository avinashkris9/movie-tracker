package com.github.avinashkris9.movietracker.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity Bean Class for Movie Details
 */
@Entity
@Table(
    name = "movieDetails",
    uniqueConstraints = {@UniqueConstraint(columnNames = "movieName")})
public class MovieDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String movieName;
  private int rating;
  @Lob private String review;
  private long externalId;

  private LocalDate lastWatched;
  private int numberOfWatch;

  public MovieDetails() {}

  public MovieDetails(
      long id,
      String movieName,
      int rating,
      String review,
      LocalDate lastWatched,
      int numberOfWatch) {
    this.id = id;
    this.movieName = movieName;
    this.rating = rating;
    this.review = review;
    this.lastWatched = lastWatched;
    this.numberOfWatch = numberOfWatch;
  }

  public long getExternalId() {
    return externalId;
  }

  public void setExternalId(long externalId) {
    this.externalId = externalId;
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

  @Override
  public String toString() {
    return "MovieDetails{"
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
        + '}';
  }
}
