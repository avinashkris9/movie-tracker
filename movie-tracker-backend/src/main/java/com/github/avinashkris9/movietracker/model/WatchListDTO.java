package com.github.avinashkris9.movietracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
@Data
public class WatchListDTO {


  private Long id;
  @NotBlank(message = "Name cannot be null")
  private String name;
  @NotNull(message = "showType must be movie or tv")
  @Pattern(regexp = "MOVIE|TV" ,message = "showType can only be movie or tv")
  private String showType;
  private LocalDate dateAdded;
  private Long externalId;

  @JsonProperty(access = Access.READ_ONLY)
  private String overView;

  @JsonProperty(access = Access.READ_ONLY)
  private String imdbId;

  @JsonProperty(access = Access.READ_ONLY)
  private String posterPath;

  @JsonProperty(access = Access.READ_ONLY)
  private String originalLanguage;
//
//  public void setShowType(String showType) {
//    this.showType = showType.toUpperCase();
//  }
}
