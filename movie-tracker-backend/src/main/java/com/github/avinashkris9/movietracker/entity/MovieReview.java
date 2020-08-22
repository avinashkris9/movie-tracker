package com.github.avinashkris9.movietracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(
    name = "movie_reviews")

@NoArgsConstructor
@AllArgsConstructor

public class MovieReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;


  @Lob
  @Column(nullable = false) //hibernate ddl will create not null constraint
  private String review;


  private LocalDate lastReviewed;

  @ManyToOne()
  @JoinColumn(name = "movie_details_id", nullable = false)
  @JsonIgnore
  private MovieDetails movieDetails;

  public Long getReviewId() {
    return reviewId;
  }

  public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public LocalDate getLastReviewed() {
    return lastReviewed;
  }

  public void setLastReviewed(LocalDate lastReviewed) {
    this.lastReviewed = lastReviewed;
  }

  public MovieDetails getMovieDetails() {
    return movieDetails;
  }

  public void setMovieDetails(MovieDetails movieDetails) {
    this.movieDetails = movieDetails;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MovieReview )) return false;
    return reviewId != null && reviewId.equals(((MovieReview) o).getReviewId());
  }

  @Override
  public int hashCode() {
    return 31;
  }


}
