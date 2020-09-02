package com.github.avinashkris9.movietracker.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 * Entity Bean Class for Movie Details
 */
@Entity
@Table(
    name = "movieDetails",
    uniqueConstraints = {@UniqueConstraint(columnNames = "movieName")})
@Data
public class MovieDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String movieName;
  private int rating;
  private long externalId;

  private LocalDate lastWatched;
  private int numberOfWatch;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movieDetails")
   private Set<MovieReview> movieReviews=new HashSet<>();

  public void addReview(MovieReview review) {
    movieReviews.add(review);
    review.setMovieDetails(this);
  }

  public void removeReview(MovieReview review) {
    movieReviews.remove(review);
    review.setMovieDetails(null);
  }


}
