package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.model.PageMovieDetailsDTO;
import com.github.avinashkris9.movietracker.service.TvService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest endpoint for TV Series
 */
@RestController
@RequestMapping("/api/tv")
@CrossOrigin
public class TvController {

  private final Logger logger = LoggerFactory.getLogger(TvController.class);
  private final TvService tvService;

  public TvController(TvService tvService) {
    this.tvService = tvService;
  }

  /**
   * Returns TV Show details with paging information.
   *
   * @param page default value is 0
   * @param size page element length. default value is 5
   * @return PageMovieDetailsDTO List of movie details dto with paging data
   */
  @GetMapping
  public PageMovieDetailsDTO getAllTvShowDetails(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size) {

    Pageable pageRequest = PageRequest.of(page, size, Sort.by("lastWatched").descending());

    return tvService.getAllTvShowsWatched(pageRequest);
  }

  /**
   * Returns the tv details for the tv show id
   *
   * @param tvId MovieId primary key
   * @return MovieDetailsDTO bean object
   * @throws com.github.avinashkris9.movietracker.exception.NotFoundException if movie not found
   */
  @GetMapping("/{tvId}")
  public MovieDetailsDTO getMovieById(@PathVariable Long tvId) {

    return tvService.getTvShowById(tvId);
  }


  /**
   * Get endpoint to search tv shows with  names. Allows partial search.
   *
   * @param name movie name to search
   * @return List of dto objects matching the movie name searched
   */
  @GetMapping("/search")
  public List<MovieDetailsDTO> searchTvshows(@RequestParam String name) {
    return tvService.getTvShowsByName(name);
  }

  /**
   * Post Endpoint to add a new movie data to db
   *
   * @param tvDetails Request body matching MovieDetailsDTO bean
   * @return MovieDetailsDTO object with additional enrichment
   */
  @PostMapping
  public MovieDetailsDTO insertNewMovieDetails(@RequestBody MovieDetailsDTO tvDetails) {

    logger.info("------ New Tv Show Received-----");
    logger.info(tvDetails.toString());
    return tvService.insertNewWatchedTvShow(tvDetails);
  }

  /**
   * PUT endpoint to update movie info using movie id
   *
   * @param tvId      Path Parameter
   * @param tvDetails Request body in MovieDetailsDTO bean
   * @return MovieDetailsDTO with updated information.
   * @throws com.github.avinashkris9.movietracker.exception.NotFoundException if no data present for
   *                                                                          movie id
   */
  @PutMapping("/{tvId}")
  public MovieDetailsDTO updateMovieDetails(
      @PathVariable long tvId, @RequestBody MovieDetailsDTO tvDetails) {

    return tvService.updateWatchedTvShow(tvDetails, tvId);
  }
//
//  /**
//   * DELETE endpoint to delete movie info using movie id
//   * @param movieId path param
//   *
//   */
//  @DeleteMapping("/{movieId}")
//  public void deleteMovieDetails(@PathVariable long movieId) {
//    movieService.deleteWatchedMovie(movieId);
//  }
}
