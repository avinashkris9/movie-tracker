package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.model.MovieReviewDTO;
import com.github.avinashkris9.movietracker.model.PageMovieDetailsDTO;
import com.github.avinashkris9.movietracker.service.MovieReviewService;
import com.github.avinashkris9.movietracker.service.MovieService;
import com.github.avinashkris9.movietracker.service.ShowManagementService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("api/movies")
@Slf4j
public class MovieController {

  private final ShowManagementService<MovieDetailsDTO, PageMovieDetailsDTO> movieService;
  private final MovieReviewService movieReviewService;

  public MovieController(MovieService movieService,MovieReviewService movieReviewService) {
    this.movieService = movieService;
    this.movieReviewService=movieReviewService;
  }

  /**
   * Returns movie details with paging information.
   * @param page default value is 0
   * @param size page element length. default value is 5
   * @return PageMovieDetailsDTO List of movie details dto with paging data
   */
  @GetMapping
  public PageMovieDetailsDTO getAllMovieDetails(@RequestParam(value = "page", defaultValue ="0") int page,@RequestParam(value = "size",defaultValue = "5") int size ) {
    Pageable pageRequest = PageRequest.of(page, size, Sort.by("lastWatched").descending());
    return movieService.getAllShowsWatched(pageRequest);
  }

  /**
   * Returns the movie details for the movie id
   *
   * @param movieId MovieId primary key
   * @return MovieDetailsDTO bean object
   * @throws com.github.avinashkris9.movietracker.exception.NotFoundException if movie not found
   */
  @GetMapping("/{movieId}")
  public MovieDetailsDTO getMovieById(@PathVariable Long movieId) {
    return movieService.getShowByShowId(movieId);
  }


  /**
   * Get endpoint to search movies with movie names.
   * Allows partial search.
   *
   * @param name movie name to search
   * @return List of dto objects matching the movie name searched
   */
  @GetMapping("/search")
  public List<MovieDetailsDTO> searchMovies(@RequestParam String name) {
    return movieService.getShowByShowName(name);
  }

  /**
   * Post Endpoint to add a new movie data to db
   * @param movieDetails Request body matching MovieDetailsDTO bean
   * @return MovieDetailsDTO object with additional enrichment
   */
  @PostMapping
  public MovieDetailsDTO insertNewMovieDetails(@RequestBody  @Valid MovieDetailsDTO movieDetails) {

    log.info("------ New Movie Received-----");
    log.info(movieDetails.toString());

    return movieService.addShowWatched(movieDetails);
  }

  /**
   * PUT endpoint to update movie info using movie id
   * @param movieId Path Parameter
   * @param movieDetails Request body in MovieDetailsDTO bean
   * @return MovieDetailsDTO with updated information.
   * @throws  com.github.avinashkris9.movietracker.exception.NotFoundException if no data present for movie id
   */
  @PutMapping("/{movieId}")
  public MovieDetailsDTO updateMovieDetails(
      @PathVariable long movieId, @Valid @RequestBody MovieDetailsDTO movieDetails) {

    return movieService.updateShowWatched(movieDetails, movieId);
  }

  /**
   * DELETE endpoint to delete movie info using movie id
   * @param movieId path param
   *
   */
  @DeleteMapping("/{movieId}")
  public void deleteMovieDetails(@PathVariable long movieId) {
    log.debug(" Delete Request for movieId {} ",movieId);
    movieService.deleteShowWatched(movieId);
  }

  @GetMapping("/{movieId}/reviews")
  public List<MovieReview> getAllMovieReviews(@PathVariable long movieId)
  {
   return movieReviewService.getAllMovieReviewsByMovieId(movieId);
  }

  @PostMapping("/{movieId}/reviews")
  MovieReviewDTO addNewMovieReview(@RequestBody MovieReviewDTO movieReview, @PathVariable long movieId)
  {
    return movieReviewService.addNewMovieReview(movieId,movieReview);
  }

  @PutMapping("/{movieId}/reviews/{reviewId}")
  MovieReviewDTO updateMovieReview(@RequestBody MovieReviewDTO movieReview, @PathVariable long movieId,@PathVariable long reviewId)
  {
    return movieReviewService.updateMovieReview(movieId,reviewId,movieReview);
  }

  @DeleteMapping("/{movieId}/reviews/{reviewId}")
  void deleteMovieReview(@PathVariable long movieId,@PathVariable long reviewId)
  {
     movieReviewService.deleteReviewById(movieId,reviewId);
  }

  @GetMapping("/dumpdata")
  List<MovieDetailsDTO> getDumps()
  {
    return null;
   // return movieService.getDumps();
  }
}
