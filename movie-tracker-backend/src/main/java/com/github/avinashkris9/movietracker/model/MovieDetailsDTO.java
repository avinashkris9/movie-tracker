package com.github.avinashkris9.movietracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieDetailsDTO {

  private long id;

  @NotBlank(message = "name must not be blank")
  private String name;
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

  @Valid
//  @JsonInclude(Include.NON_NULL)
  private Set<MovieReviewDTO> reviews;

  @JsonProperty(access = Access.READ_ONLY)
  private String releaseDate;
}
