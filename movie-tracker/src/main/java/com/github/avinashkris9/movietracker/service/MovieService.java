package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.exception.EntityExistsException;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDB;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  private final MovieRepository movieRepository;
  private final TheMovieDBService theMovieDBService;
  private final CustomModelMapper customModelMapper;
  //initialise logger.
  Logger logger = LoggerFactory.getLogger(MovieService.class);

  public MovieService(MovieRepository movieRepository, TheMovieDBService theMovieDBService,
      CustomModelMapper customModelMapper) {
    this.movieRepository = movieRepository;
    this.theMovieDBService = theMovieDBService;
    this.customModelMapper = customModelMapper;
  }

  public MovieDetailsDTO insertNewWatchedMovie(MovieDetails movieDetails) {

    if (Objects.isNull(movieDetails.getLastWatched())) {
      movieDetails.setLastWatched(LocalDate.now());
    }
    movieDetails.setNumberOfWatch(1);

    // call themoviedb api and find out the movie ID.
    //TODO , find a better solution rather than using search api.
    MovieDetails movieName = movieRepository.findByMovieName(movieDetails.getMovieName());

    if (!Objects.isNull(movieName)) {
      logger.error(" Movie {} exists in db {}", movieName.getMovieName(), movieName.getId());
      throw new EntityExistsException("MOVIE_EXISTS");
    }

    MovieDBDetails optionalMovieDBDetails = theMovieDBService
        .getMovieDetailsBySearch(movieDetails.getMovieName());

    if (optionalMovieDBDetails.getMovieDBDetails().size() != 0) {
      long themovieDBMovieId = optionalMovieDBDetails.getMovieDBDetails().get(0).getMovieId();
      movieDetails.setExternalId(themovieDBMovieId);

    }

    movieRepository.save(movieDetails);

    return customModelMapper.MovieEntity2MovieDTO(movieDetails);

  }

  public void updateWatchedMovie(MovieDetails movieDetails, long movieId) {
    movieDetails.setId(movieId);
    if (Objects.isNull(movieDetails.getLastWatched())) {
      movieDetails.setLastWatched(LocalDate.now());
    }
    movieDetails.setNumberOfWatch(movieDetails.getNumberOfWatch() + 1);
    movieRepository.save(movieDetails);
  }


  public MovieDetailsDTO getMovieById(long movieId) {
    Optional<MovieDetails> movieDetails = movieRepository.findById(movieId);
    if (movieDetails.isPresent()) {

      MovieDetailsDTO movieDetailsDTO = customModelMapper.MovieEntity2MovieDTO(movieDetails.get());
      long externalMovieId = movieDetails.get().getExternalId();
      if (externalMovieId != 0) {
        appendTheMovieDBData(movieDetailsDTO);
      }

      return movieDetailsDTO;
    }
    throw new NotFoundException("ERR_404");

  }


  /**
   * Search or query using movie name
   *
   * @return
   */

  public List<MovieDetailsDTO> getMoviesByMovieName(String movieName) {
    List<MovieDetails> moviesByName = movieRepository.findByMovieNameContainsIgnoreCase(movieName);

    if (moviesByName.size() == 0) {
      throw new NotFoundException("No movies");
    }
    List<MovieDetailsDTO> movieDetailsDTOS = new ArrayList<>();
    for (MovieDetails md : moviesByName) {
      MovieDetailsDTO movieDetailsDTO = new MovieDetailsDTO();
      movieDetailsDTO = customModelMapper.MovieEntity2MovieDTO(md);
      appendTheMovieDBData(movieDetailsDTO);


    }

    return null;

  }

  public List<MovieDetailsDTO> getAllMoviesWatched() {
    List<MovieDetails> mv = movieRepository.findAll();
    if (mv.size() == 0) {
      throw new NotFoundException("ERR_404");

    }
    List<MovieDetailsDTO> movieDetailsDTOS = new ArrayList<>();
    for (MovieDetails movie : mv) {
      MovieDetailsDTO movieDetailsDTO = customModelMapper.MovieEntity2MovieDTO(movie);
      long externalMovieId = movie.getExternalId();
      if (externalMovieId != 0) {
        appendTheMovieDBData(movieDetailsDTO);
      }

      movieDetailsDTOS.add(movieDetailsDTO);
    }
    return movieDetailsDTOS;
  }

  public void deleteWatchedMovie(long movieId) {
    Optional<MovieDetails> movieDetails = movieRepository.findById(movieId);
    movieDetails.orElseThrow(() -> new NotFoundException("Not found"));

    movieRepository.delete(movieDetails.get());
  }


  public MovieDetailsDTO appendTheMovieDBData(MovieDetailsDTO movieDetailsDTO) {
    MovieDB movieDB = theMovieDBService.getMovieById(movieDetailsDTO.getExternalId());
    movieDetailsDTO.setOverView(movieDB.getMovieSummary());
    movieDetailsDTO.setImdbId(movieDB.getImdbId());
    movieDetailsDTO.setPosterPath(theMovieDBService.moviePosterPath(movieDB.getPosterPath()));
    movieDetailsDTO.setOriginalLanguage(movieDB.getOriginalLanguge());
    return movieDetailsDTO;

  }


}
