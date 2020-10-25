package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.model.MovieReviewDTO;
import com.github.avinashkris9.movietracker.model.PageMovieDetailsDTO;
import com.github.avinashkris9.movietracker.service.TvReviewService;
import com.github.avinashkris9.movietracker.service.TvService;
import java.util.List;
import lombok.AllArgsConstructor;
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

/**
 * Rest endpoint for TV Series
 */
@RestController
@RequestMapping("/api/tv")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class TvController {


  private final TvService tvService;
  private final TvReviewService tvReviewService;


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

    log.info("------ New Tv Show Received-----");
    log.info(tvDetails.toString());
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

  /**
   * DELETE endpoint to delete movie info using movie id
   * @param tvId path param
   *
   */
  @DeleteMapping("/{tvId}")
  public void deleteMovieDetails(@PathVariable long tvId) {
    log.debug(" Delete Tv Request for {}",tvId );
    tvService.deleteWatchedTv(tvId);
  }




  @GetMapping("/{tvId}/reviews")
  public List<MovieReviewDTO> getAllTvReviews(@PathVariable long tvId)
  {
    return tvReviewService.getAllTvReviewsByTvId(tvId);
  }

  @PostMapping("/{tvId}/reviews")
  MovieReviewDTO addNewTvReview(@RequestBody MovieReviewDTO tvReview, @PathVariable long tvId)
  {
    return tvReviewService.addNewTvReview(tvId,tvReview);
  }

  @PutMapping("/{tvId}/reviews/{reviewId}")
  MovieReviewDTO updateTvReview(@RequestBody MovieReviewDTO tvReview, @PathVariable long tvId,@PathVariable long reviewId)
  {
    return tvReviewService.updateTvReview(tvId,reviewId,tvReview);
  }

  @DeleteMapping("/{tvId}/reviews/{reviewId}")
  void deleteReviewById(@PathVariable long tvId,@PathVariable long reviewId)
  {
    tvReviewService.deleteReviewById(tvId,reviewId);
  }
}
