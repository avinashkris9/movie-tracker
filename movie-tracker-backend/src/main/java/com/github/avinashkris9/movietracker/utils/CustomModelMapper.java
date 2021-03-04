package com.github.avinashkris9.movietracker.utils;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.entity.TvDetails;
import com.github.avinashkris9.movietracker.entity.TvReview;
import com.github.avinashkris9.movietracker.entity.WatchList;
import com.github.avinashkris9.movietracker.model.MovieResponse;
import com.github.avinashkris9.movietracker.model.MovieReviewResponse;
import com.github.avinashkris9.movietracker.model.WatchListDTO;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper {

  public MovieDetails movieDTO2MovieEntity(MovieResponse movieResponse) {

    if (movieResponse == null) return null;
    else {
      MovieDetails movieDetails = new MovieDetails();

      movieDetails.setLastWatched(movieResponse.getLastWatched());
      movieDetails.setNumberOfWatch(movieResponse.getNumberOfWatch());
      movieDetails.setExternalId(movieResponse.getExternalId());
      movieDetails.setMovieName(movieResponse.getName());
      movieDetails.setRating(movieResponse.getRating());

      if (!movieDetails.getMovieReviews().isEmpty()) {
        movieDetails.setMovieReviews(
            movieResponse.getReviews().stream()
                .map(this::movieReviewDTO2MovieReviewEntity)
                .collect(Collectors.toSet()));
      }
      return movieDetails;
    }
  }

  public MovieResponse movieEntity2MovieDTO(MovieDetails movieDetails) {

    if (movieDetails == null) return null;
    else {


    MovieResponse details = new MovieResponse();
    details.setLastWatched(movieDetails.getLastWatched());
    details.setNumberOfWatch(movieDetails.getNumberOfWatch());
    details.setExternalId(movieDetails.getExternalId());
    details.setName(movieDetails.getMovieName());
    details.setRating(movieDetails.getRating());

    if (!movieDetails.getMovieReviews().isEmpty()) {
      details.setReviews(
          movieDetails.getMovieReviews().stream()
              .map(this::movieReviewEntity2MoviewReviewDTO)
              .collect(Collectors.toSet()));
    }
    details.setId(movieDetails.getId());
    return details;
    }
  }

  public TvDetails movieDetailsDTO2TvEntity(MovieResponse movieResponse) {

    if (movieResponse == null) return null;
    else {

      TvDetails tvDetails = new TvDetails();

      tvDetails.setLastWatched(movieResponse.getLastWatched());
      tvDetails.setExternalId(movieResponse.getExternalId());
      tvDetails.setTvShowName(movieResponse.getName());
      tvDetails.setRating(movieResponse.getRating());

      if (!tvDetails.getTvReviews().isEmpty()) {
        tvDetails.setTvReviews(
            movieResponse.getReviews().stream()
                .map(this::movieReviewDTO2TvReviewEntity)
                .collect(Collectors.toSet()));
      }
      return tvDetails;
    }
  }

  public MovieResponse tvEntity2MovieDTO(TvDetails tvDetails) {

    if (tvDetails == null) return null;
    else {

      MovieResponse details = new MovieResponse();
      details.setLastWatched(tvDetails.getLastWatched());

      details.setExternalId(tvDetails.getExternalId());
      details.setName(tvDetails.getTvShowName());
      details.setRating(tvDetails.getRating());
      //    details.setReview(tvDetails.getReview());
      details.setId(tvDetails.getId());
      if (!tvDetails.getTvReviews().isEmpty()) {
        details.setReviews(
            tvDetails.getTvReviews().stream()
                .map(this::tvReviewEntity2MoviewReviewDTO)
                .collect(Collectors.toSet()));
      }
      return details;
    }
  }

  public WatchList watchListDTO2WatchListEntity(WatchListDTO watchListDTO)
  {

    if (watchListDTO == null) return null;
    else {

      WatchList watchList = new WatchList();
      watchList.setName(watchListDTO.getName());
      watchList.setDateAdded(watchListDTO.getDateAdded());
      watchList.setShowType(watchListDTO.getShowType());
      watchList.setExternalId(watchListDTO.getExternalId());
      return watchList;
    }
  }

  public WatchListDTO watchListEntity2WatchListDTO(WatchList watchListEntity)
  {

    if (watchListEntity == null) return null;
    else {
      WatchListDTO watchList = new WatchListDTO();
      watchList.setName(watchListEntity.getName());
      watchList.setDateAdded(watchListEntity.getDateAdded());
      watchList.setShowType(watchListEntity.getShowType());
      watchList.setExternalId(watchListEntity.getExternalId());
      watchList.setId(watchListEntity.getId());
      return watchList;
    }
  }



  public MovieReview movieReviewDTO2MovieReviewEntity(MovieReviewResponse movieReviewResponse)
  {
    if (movieReviewResponse == null) return null;
    else {
      MovieReview movieReview = new MovieReview();
      movieReview.setReview(movieReviewResponse.getReview());
      movieReview.setReviewId(movieReviewResponse.getReviewId());
      movieReview.setLastReviewed(movieReviewResponse.getLastReviewed());
      return movieReview;
    }
  }

  public MovieReviewResponse movieReviewEntity2MoviewReviewDTO(MovieReview movieReview)
  {
    if (movieReview == null) return null;
    else {
      MovieReviewResponse movieReviewResponse = new MovieReviewResponse();
      movieReviewResponse.setReviewId(movieReview.getReviewId());
      movieReviewResponse.setReview(movieReview.getReview());
      movieReviewResponse.setLastReviewed(movieReview.getLastReviewed());
      return movieReviewResponse;
    }
  }

  public TvReview movieReviewDTO2TvReviewEntity(MovieReviewResponse movieReviewResponse)
  {
    if (movieReviewResponse == null) return null;
    else {
      TvReview movieReview = new TvReview();
      movieReview.setReview(movieReviewResponse.getReview());
      movieReview.setReviewId(movieReviewResponse.getReviewId());
      movieReview.setLastReviewed(movieReviewResponse.getLastReviewed());
      return movieReview;
    }
  }

  public MovieReviewResponse tvReviewEntity2MoviewReviewDTO(TvReview movieReview)
  {
    if (movieReview == null) return null;
    else {
      MovieReviewResponse movieReviewResponse = new MovieReviewResponse();
      movieReviewResponse.setReviewId(movieReview.getReviewId());
      movieReviewResponse.setReview(movieReview.getReview());
      movieReviewResponse.setLastReviewed(movieReview.getLastReviewed());
      return movieReviewResponse;

    }
  }

}
