package com.github.avinashkris9.movietracker.utils;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper {

  public MovieDetails MovieDTO2MovieEntity(MovieDetailsDTO movieDetailsDTO) {
    MovieDetails movieDetails = new MovieDetails();

    movieDetails.setLastWatched(movieDetailsDTO.getLastWatched());
    movieDetails.setNumberOfWatch(movieDetailsDTO.getNumberOfWatch());

    movieDetails.setMovieName(movieDetailsDTO.getMovieName());
    movieDetails.setRating(movieDetailsDTO.getRating());
    movieDetails.setReview(movieDetailsDTO.getReview());
    return movieDetails;
  }


  public MovieDetailsDTO MovieEntity2MovieDTO(MovieDetails movieDetails) {
    MovieDetailsDTO details = new MovieDetailsDTO();
    details.setLastWatched(movieDetails.getLastWatched());
    details.setNumberOfWatch(movieDetails.getNumberOfWatch());
    details.setExternalId(movieDetails.getExternalId());
    details.setMovieName(movieDetails.getMovieName());
    details.setRating(movieDetails.getRating());
    details.setReview(movieDetails.getReview());
    details.setId(movieDetails.getId());
    return details;
  }
}
