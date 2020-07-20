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
  @Lob private String review;
  private long externalId;

  private LocalDate lastWatched;
  private int numberOfWatch;


}
