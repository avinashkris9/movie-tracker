package com.github.avinashkris9.movietracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDB {

  @JsonProperty("id")
  private long movieId;

  @JsonProperty("overview")
  private String movieSummary;

  @JsonProperty("imdb_id")
  private String imdbId;

  private String title;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("original_language")
  private String originalLanguge;

  public MovieDB() {}

  public MovieDB(
      long movieId,
      String movieSummary,
      String imdbId,
      String title,
      String posterPath,
      String originalLanguge) {
    this.movieId = movieId;
    this.movieSummary = movieSummary;
    this.imdbId = imdbId;
    this.title = title;
    this.posterPath = posterPath;
    this.originalLanguge = originalLanguge;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getOriginalLanguge() {
    return originalLanguge;
  }

  public void setOriginalLanguge(String originalLanguge) {
    this.originalLanguge = originalLanguge;
  }

  public long getMovieId() {
    return movieId;
  }

  public void setMovieId(long movieId) {
    this.movieId = movieId;
  }

  public String getMovieSummary() {
    return movieSummary;
  }

  public void setMovieSummary(String movieSummary) {
    this.movieSummary = movieSummary;
  }

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "MovieDB{"
        + "movieId="
        + movieId
        + ", movieSummary='"
        + movieSummary
        + '\''
        + ", imdbId='"
        + imdbId
        + '\''
        + ", title='"
        + title
        + '\''
        + ", posterPath='"
        + posterPath
        + '\''
        + ", originalLanguge='"
        + originalLanguge
        + '\''
        + '}';
  }
}
