package com.github.avinashkris9.movietracker.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 * Bean class for TVShow entity
 */
@Entity
@Table(
    name = "tvshows",
    uniqueConstraints = {@UniqueConstraint(columnNames = "tvShowName")})
@Data
public class TvDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String tvShowName;

  private String platform;
  private LocalDate lastWatched;
  private boolean isCompleted;
  // @Lob
  @Column(nullable = false,columnDefinition = "clob")
  private String review;
  private long externalId;
  private int rating;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tvDetails")
  private Set<TvReview> tvReviews=new HashSet<>();


  public void addReview(TvReview review) {
    tvReviews.add(review);
    review.setTvDetails(this);
  }

  public void removeReview(TvReview review) {
    tvReviews.remove(review);
    review.setTvDetails(null);
  }

}
