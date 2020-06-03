package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.service.MovieService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/movies")
public class MovieController {


  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping
  public List<MovieDetailsDTO> getAllMovieDetails() {
    return movieService.getAllMoviesWatched();
  }

  @GetMapping("/{movieId}")
  public MovieDetailsDTO getMovieById(@PathVariable Long movieId) {
    return movieService.getMovieById(movieId);
  }

  @GetMapping("/search")
  public List<MovieDetailsDTO> searchMovies(@RequestParam String name) {
    return movieService.getMoviesByMovieName(name);
  }

  @PostMapping
  public MovieDetailsDTO insertNewMovieDetails(@RequestBody MovieDetails movieDetails) {

    return movieService.insertNewWatchedMovie(movieDetails);

  }


  @PutMapping("/{movieId}")
  public void updateMovieDetails(@PathVariable long movieId,
      @RequestBody MovieDetails movieDetails) {

    movieService.updateWatchedMovie(movieDetails, movieId);
  }

  @DeleteMapping("/{movieId}")
  public void deleteMovieDetails(@PathVariable long movieId) {
    movieService.deleteWatchedMovie(movieId);
  }
}
