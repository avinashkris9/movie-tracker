package com.github.avinashkris9.movietracker.model;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import java.time.LocalDate;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class MovieReviewResponse {


  private Long reviewId;

  @NotBlank
  private String review;
  private LocalDate lastReviewed;




}
