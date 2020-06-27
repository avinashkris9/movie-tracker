package com.github.avinashkris9.movietracker.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
  @Lob
  private String review;
  private long externalId;
  private int rating;


}
