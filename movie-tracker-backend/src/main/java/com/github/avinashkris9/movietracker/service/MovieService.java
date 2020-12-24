package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.exception.EntityExistsException;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.model.PageMovieDetailsDTO;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MovieService {

  private final MovieRepository movieRepository;
  private final TheMovieDBService theMovieDBService;
  private final CustomModelMapper customModelMapper;

  public MovieService(
      MovieRepository movieRepository,
      TheMovieDBService theMovieDBService,
      CustomModelMapper customModelMapper) {
    this.movieRepository = movieRepository;
    this.theMovieDBService = theMovieDBService;
    this.customModelMapper = customModelMapper;
  }

  /**
   * Inserts movie details to db. TheMovieDB API is being called with the movie name to perform
   * additional enrichment. Sets default value for LastWatched,NumerOfWatch fields if not present.
   *
   * @param movieDetails DTO object
   * @return DTO object with extra enrichment information
   * @throws EntityExistsException if same movie name present in database.
   */



  public MovieDetailsDTO insertNewWatchedMovie(MovieDetailsDTO movieDetails) {

    if (Objects.isNull(movieDetails.getLastWatched())) {
      log.info(" No watched date provided so setting today's date");
      movieDetails.setLastWatched(LocalDate.now());
    }
    movieDetails.setNumberOfWatch(1);
    MovieDetails movieName = movieRepository.findByMovieName(movieDetails.getName());
    if (!Objects.isNull(movieName)) {
      log.error(" Movie {} exists in db {}", movieName.getMovieName(), movieName.getId());
      throw new EntityExistsException("MOVIE_EXISTS");
    }
    // if external id is already populated. Trust the client.
    if (movieDetails.getExternalId() == 0) {
      // call themoviedb api and find out the movie ID.
      // TODO , find a better solution rather than using search api.
      MovieDBDetails optionalMovieDBDetails =
          theMovieDBService.getMovieDetailsBySearch(
              movieDetails.getName(), SHOW_TYPES.MOVIE.name());

      if (!optionalMovieDBDetails.getMovieDBDetails().isEmpty()) {
        long themovieDBMovieId = optionalMovieDBDetails.getMovieDBDetails().get(0).getMovieId();
        log.debug("The movie db entry found with external id {} ", themovieDBMovieId);
        movieDetails.setExternalId(themovieDBMovieId);

      }
    }

    MovieDetails movieDetailsEntity=customModelMapper.movieDTO2MovieEntity(movieDetails);
    if(!(movieDetails.getReview() ==null || movieDetails.getReview().isEmpty()))
    {
      MovieReview movieReview=new MovieReview();
      movieReview.setMovieDetails(movieDetailsEntity);
      movieReview.setLastReviewed(movieDetails.getLastWatched());
      movieReview.setReview(movieDetails.getReview());
      movieDetailsEntity.getMovieReviews().add(movieReview);
    }


    movieDetailsEntity =
        movieRepository.save(movieDetailsEntity);
    log.info("Sasa {}", movieDetails);
    movieDetails.setId(movieDetailsEntity.getId());
    return customModelMapper.movieEntity2MovieDTO(movieDetailsEntity);
  }

  /**
   * Update database with the new information received
   *
   * @param movieDetailsDTO DTO object for movie
   * @param movieId primary key to identify database entry
   * @return MovieDetailsDTO DTO object
   */
  public MovieDetailsDTO updateWatchedMovie(MovieDetailsDTO movieDetailsDTO, long movieId) {

    Optional<MovieDetails> movieFromDb = movieRepository.findById(movieId);
    LocalDate today = LocalDate.now();
    if (!movieFromDb.isPresent()) {
      throw new NotFoundException("MOVIE_NOT_FOUND");
    }
    MovieDetails movieEntityFromDb =movieFromDb.get();
    log.debug("DB Entry iss {}", movieEntityFromDb);
    if (Objects.isNull(movieDetailsDTO.getLastWatched())) {
      log.info("No date provided. setting LastWatched data as today");
      movieEntityFromDb.setLastWatched(today);
    } else {
      // if data is older than what's in db. Don't replace it.
      if (!movieDetailsDTO.getLastWatched().isBefore(movieEntityFromDb.getLastWatched())) {
        movieEntityFromDb.setLastWatched(movieDetailsDTO.getLastWatched());
      }
    }

    movieEntityFromDb.setNumberOfWatch(movieDetailsDTO.getNumberOfWatch() + 1);
    log.info("update movie details {}", movieEntityFromDb);



    if(!(movieDetailsDTO.getReview() ==null || movieDetailsDTO.getReview().isEmpty()))
    {
      MovieReview movieReview=new MovieReview();
      movieReview.setMovieDetails(movieEntityFromDb);
      movieReview.setLastReviewed(movieEntityFromDb.getLastWatched());
      movieReview.setReview(movieDetailsDTO.getReview());
//      movieDetails1.getMovieReviews().add(movieReview);
      movieEntityFromDb.addReview(movieReview);

    }

//    for (MovieReview movieReview : movieDetails1.getMovieReviews()) {
//      //
//      System.out.println(movieReview.getReview());
//    }

    return customModelMapper.movieEntity2MovieDTO(movieRepository.save(movieEntityFromDb));
  }

  /**
   * Retrive movie details from db using the movie id TheMovieDB data is appended if externalMovieId
   * field holds the TheMovieDB id
   *
   * @param movieId Primary Key id for movie
   * @return DTO object for movie details
   * @throws NotFoundException if no entry found for movieId
   */
  public MovieDetailsDTO getMovieById(long movieId) {
    Optional<MovieDetails> movieDetails = movieRepository.findById(movieId);
    if (movieDetails.isPresent()) {
      log.debug(movieDetails.toString());
      MovieDetailsDTO movieDetailsDTO = customModelMapper.movieEntity2MovieDTO(movieDetails.get());
      long externalMovieId = movieDetails.get().getExternalId();
      if (externalMovieId != 0) {
        theMovieDBService.appendTheMovieDBData(movieDetailsDTO, SHOW_TYPES.MOVIE.name());
      }
      return movieDetailsDTO;
    }
    throw new NotFoundException("ERR_404");
  }

  /**
   * Search or query using movie name Allows partial search using name
   *
   * @return List of all movies matching the movie name
   * @throws NotFoundException if no movies are found for the search string
   */
  public List<MovieDetailsDTO> getMoviesByMovieName(String movieName) {
    List<MovieDetails> moviesByName = movieRepository.findByMovieNameContainsIgnoreCase(movieName);
    if (moviesByName.isEmpty()) {
      throw new NotFoundException("No movies");
    }
    List<MovieDetailsDTO> movieDetailsDTOS = new ArrayList<>();
    for (MovieDetails md : moviesByName) {
      MovieDetailsDTO movieDetailsDTO = customModelMapper.movieEntity2MovieDTO(md);
      theMovieDBService.appendTheMovieDBData(movieDetailsDTO, SHOW_TYPES.MOVIE.name());
      movieDetailsDTOS.add(movieDetailsDTO);
    }

    return movieDetailsDTOS;
  }

  /**
   * Provide all movie Details in database as paginated data
   *
   * @param pageRequest Pageable object with page number and page size
   * @return PageMovieDetailsDTO object holding list of movies and paging information
   * @throws NotFoundException if no movies found in database
   */
  public PageMovieDetailsDTO getAllMoviesWatched(Pageable pageRequest) {

    Page<MovieDetails> movieDetails = movieRepository.findAll(pageRequest);
    // throw exception if there are no movies
    // @TODO -> use enum for error code
    if (movieDetails.getSize() == 0 || !movieDetails.hasContent()) {
      throw new NotFoundException("ERR_404");
    }
    log.info(movieDetails.getContent().toString());
    List<MovieDetailsDTO> movieDetailsDTOList =
        movieDetails.getContent().stream()
            .map(theMovieDBService::transformMovieEntity)
            .collect(Collectors.toList());
    return new PageMovieDetailsDTO(
        movieDetailsDTOList, movieDetails.getTotalElements(), movieDetails.getTotalPages());
  }

  /**
   * Delete movie information from database using primary key
   *
   * @param movieId primary key
   * @throws NotFoundException if no movie matching movieId present in db.
   */
  public void deleteWatchedMovie(long movieId) {
    Optional<MovieDetails> movieDetails = movieRepository.findById(movieId);
    movieDetails.orElseThrow(() -> new NotFoundException("Not found"));
    movieRepository.delete(movieDetails.get());
  }

  public long getMovieCount() {
    return movieRepository.count();
  }

  public long getTopRatedMovieCount() {
    return movieRepository.countByRating(5);
  }


  public Map<Integer,Long> getMonthlyCount()
  {
    List<Object[]> output=movieRepository.monthlyCount();


    //@TODO Optimise
    return  output.stream().collect(Collectors.toMap( x -> (Integer)x[0] , x -> (Long)x[1]));
  }


  public List<MovieDetailsDTO> getDumps()
  {
    List<MovieDetails> movieDetails= movieRepository.findAll();
    if(movieDetails.isEmpty())
    {

      throw new NotFoundException("ERR_404");
    }

    List<MovieDetailsDTO> movieDetailsDTOList =
        movieDetails.stream()
            .map(
                x-> customModelMapper.movieEntity2MovieDTO(x))
            .collect(Collectors.toList());
    return movieDetailsDTOList;
  }

}
