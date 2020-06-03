package com.github.avinashkris9.movietracker.repository;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieDetails, Long> {

  MovieDetails findByMovieName(String movieName);

  List<MovieDetails> findAll();

  List<MovieDetails> findByMovieNameContainsIgnoreCase(String movieName);
}
