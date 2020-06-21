package com.github.avinashkris9.movietracker.utils;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.entity.TvDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper {

  public MovieDetails movieDTO2MovieEntity(MovieDetailsDTO movieDetailsDTO) {
    MovieDetails movieDetails = new MovieDetails();

    movieDetails.setLastWatched(movieDetailsDTO.getLastWatched());
    movieDetails.setNumberOfWatch(movieDetailsDTO.getNumberOfWatch());
    movieDetails.setExternalId(movieDetailsDTO.getExternalId());
    movieDetails.setMovieName(movieDetailsDTO.getName());
    movieDetails.setRating(movieDetailsDTO.getRating());
    movieDetails.setReview(movieDetailsDTO.getReview());
    return movieDetails;
  }

  public MovieDetailsDTO movieEntity2MovieDTO(MovieDetails movieDetails) {
    MovieDetailsDTO details = new MovieDetailsDTO();
    details.setLastWatched(movieDetails.getLastWatched());
    details.setNumberOfWatch(movieDetails.getNumberOfWatch());
    details.setExternalId(movieDetails.getExternalId());
    details.setName(movieDetails.getMovieName());
    details.setRating(movieDetails.getRating());
    details.setReview(movieDetails.getReview());
    details.setId(movieDetails.getId());
    return details;
  }

  public TvDetails movieDetailsDTO2TvEntity(MovieDetailsDTO movieDetailsDTO) {
    TvDetails tvDetails = new TvDetails();

    tvDetails.setLastWatched(movieDetailsDTO.getLastWatched());

    tvDetails.setExternalId(movieDetailsDTO.getExternalId());
    tvDetails.setTvShowName(movieDetailsDTO.getName());
    tvDetails.setRating(movieDetailsDTO.getRating());
    tvDetails.setReview(movieDetailsDTO.getReview());
    return tvDetails;
  }

  public MovieDetailsDTO tvEntity2MovieDTO(TvDetails tvDetails) {
    MovieDetailsDTO details = new MovieDetailsDTO();
    details.setLastWatched(tvDetails.getLastWatched());

    details.setExternalId(tvDetails.getExternalId());
    details.setName(tvDetails.getTvShowName());
    details.setRating(tvDetails.getRating());
    details.setReview(tvDetails.getReview());
    details.setId(tvDetails.getId());
    return details;
  }
}
