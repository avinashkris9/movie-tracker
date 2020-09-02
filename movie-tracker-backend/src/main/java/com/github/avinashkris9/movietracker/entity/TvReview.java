package com.github.avinashkris9.movietracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(
    name = "tv_reviews")



@NoArgsConstructor
@AllArgsConstructor
public class TvReview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;


  @Lob
  @Column(nullable = false) //hibernate ddl will create not null constraint
  private String review;


  private LocalDate lastReviewed;

  @ManyToOne()
  @JoinColumn(name = "tv_details_id", nullable = false)
  @JsonIgnore
  private TvDetails tvDetails;

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

  public TvDetails getTvDetails() {
    return tvDetails;
  }

  public void setTvDetails(TvDetails tvDetails) {
    this.tvDetails = tvDetails;
  }


 
}
