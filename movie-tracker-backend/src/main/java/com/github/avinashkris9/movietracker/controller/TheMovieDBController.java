package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDB;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.service.TheMovieDBService;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import java.net.URISyntaxException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Rest End Points for direct moviedb actions.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/themoviedb")
@Slf4j
public class TheMovieDBController {

  private final RestTemplate restTemplate;
  private final TheMovieDBService theMovieDBService;

  public TheMovieDBController(RestTemplate restTemplate, TheMovieDBService theMovieDBService) {
    this.restTemplate = restTemplate;
    this.theMovieDBService = theMovieDBService;
  }

  @GetMapping("/movies/{movieId}")
  public MovieDB getMovieDetails(@PathVariable long movieId) throws URISyntaxException {

    MovieDB movieDB =
        restTemplate.getForObject(
            theMovieDBService.movieIdUrl(movieId, SHOW_TYPES.MOVIE.name()), MovieDB.class);

    if (Objects.isNull(movieDB)) {
      throw new NotFoundException("MovieDB entry not found");
    }
    return movieDB;
  }

  @GetMapping("/search")
  public MovieDBDetails searchMovieDB(@RequestParam("name") String movieName,
      @RequestParam("showType") String showType) {

    log.info("Search Request for {} show {}", showType, movieName);

    MovieDBDetails movieDBDetails =
        theMovieDBService.getMovieDetailsBySearch(movieName, showType);

    if(!movieDBDetails.getMovieDBDetails().isEmpty())
    {

    movieDBDetails
        .getMovieDBDetails()
        .forEach(
            f -> {
              if (f.getPosterPath() != null) {
                f.setPosterPath(theMovieDBService.moviePosterPath(f.getPosterPath()));
              }
            });
    }

    return movieDBDetails;
  }
}
