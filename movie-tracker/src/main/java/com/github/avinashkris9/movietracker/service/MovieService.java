package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.exception.EntityExistsException;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDB;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.model.PageMovieDetailsDTO;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  private final MovieRepository movieRepository;
  private final TheMovieDBService theMovieDBService;
  private final CustomModelMapper customModelMapper;
  // initialise logger.
  Logger logger = LoggerFactory.getLogger(MovieService.class);

  public MovieService(
      MovieRepository movieRepository,
      TheMovieDBService theMovieDBService,
      CustomModelMapper customModelMapper) {
    this.movieRepository = movieRepository;
    this.theMovieDBService = theMovieDBService;
    this.customModelMapper = customModelMapper;
  }

  /**
   * Inserts movie details to db.
   * TheMovieDB API is being called with the movie name to perform additional enrichment.
   * Sets default value for LastWatched,NumerOfWatch fields if not present.
   * @param movieDetails DTO object
   * @return DTO object with extra enrichment information
   * @throws EntityExistsException if same movie name present in database.
   */
  public MovieDetailsDTO insertNewWatchedMovie(MovieDetailsDTO movieDetails) {

    if (Objects.isNull(movieDetails.getLastWatched())) {
      logger.info(" No watched date provided so setting today's date");
      movieDetails.setLastWatched(LocalDate.now());
    }
    movieDetails.setNumberOfWatch(1);

    // call themoviedb api and find out the movie ID.
    // TODO , find a better solution rather than using search api.
    MovieDetails movieName = movieRepository.findByMovieName(movieDetails.getMovieName());

    if (!Objects.isNull(movieName)) {
      logger.error(" Movie {} exists in db {}", movieName.getMovieName(), movieName.getId());
      throw new EntityExistsException("MOVIE_EXISTS");
    }

    MovieDBDetails optionalMovieDBDetails =
        theMovieDBService.getMovieDetailsBySearch(movieDetails.getMovieName());

    System.out.println(optionalMovieDBDetails.toString());
    if (!optionalMovieDBDetails.getMovieDBDetails().isEmpty()) {
      long themovieDBMovieId = optionalMovieDBDetails.getMovieDBDetails().get(0).getMovieId();
      logger.debug("The movie db entry found with external id {} ",themovieDBMovieId);
      movieDetails.setExternalId(themovieDBMovieId);
    }

   MovieDetails movieDetails1= movieRepository.save(customModelMapper.MovieDTO2MovieEntity(movieDetails));
    logger.info("Sasa {}", movieDetails);
    movieDetails.setId(movieDetails1.getId());
    return movieDetails;
  }

  /**
   * Update database with the new information received
   *
   * @param movieDetailsDTO DTO object for movie
   * @param movieId primary key to identify database entry
   * @return MovieDetailsDTO DTO object
   *
   */
  public MovieDetailsDTO updateWatchedMovie(MovieDetailsDTO movieDetailsDTO, long movieId) {

    //    @TODO Exception need to be thrown if movie id is not found

    Optional<MovieDetails> movieFromDb = movieRepository.findById(movieId);
    LocalDate today=LocalDate.now();
    if(movieFromDb.isEmpty())
    {
      throw new NotFoundException("MOVIE_NOT_FOUND");
    }

    if (Objects.isNull(movieDetailsDTO.getLastWatched())) {
      logger.info("No date provided. setting LastWatched data as today");
      movieDetailsDTO.setLastWatched(today);
    }
    else
    {
      //if data is older than what's in db. Don't replace it.
      if(movieDetailsDTO.getLastWatched().isBefore(movieFromDb.get().getLastWatched()))
      {
        movieDetailsDTO.setLastWatched(movieFromDb.get().getLastWatched());
      }
    }


    movieDetailsDTO.setNumberOfWatch(movieDetailsDTO.getNumberOfWatch() + 1);
    logger.info("update movie details {}", movieDetailsDTO);
    MovieDetails movieDetails1 = customModelMapper.MovieDTO2MovieEntity(movieDetailsDTO);
    movieDetails1.setId(movieId);
    movieRepository.save(movieDetails1);
    return movieDetailsDTO;
  }


  /**
   * Retrive movie details from db using the movie id
   * TheMovieDB data is appended if externalMovieId field holds the TheMovieDB id
   * @param movieId Primary Key id for movie
   * @return DTO object for movie details
   * @throws NotFoundException if no entry found for movieId
   */
  public MovieDetailsDTO getMovieById(long movieId) {
    Optional<MovieDetails> movieDetails = movieRepository.findById(movieId);
    if (movieDetails.isPresent()) {
      logger.debug(movieDetails.toString());
      System.out.println(movieDetails.toString());
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
   * Allows partial search using name
   * @return List of all movies matching the movie name
   * @throws NotFoundException if no movies are found for the search string
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
      movieDetailsDTOS.add(movieDetailsDTO);
    }

    return movieDetailsDTOS ;
  }

  /**
   * Provide all movie Details in database as paginated data
   * @param pageRequest Pageable object with page number and page size
   * @return PageMovieDetailsDTO object holding list of movies  and paging information
   * @throws NotFoundException if no movies found in database
   */
  public PageMovieDetailsDTO getAllMoviesWatched(Pageable pageRequest) {

    //    Pageable secondPageWithFiveElements = PageRequest.of(1, 5);
    //
    //
    //    List<MovieDetails> mv = movieRepository.findAll(secondPageWithFiveElements);
    //    if (mv.size() == 0) {
    //      throw new NotFoundException("ERR_404");
    //    }
    //    List<MovieDetailsDTO> movieDetailsDTOS = new ArrayList<>();
    //    for (MovieDetails movie : mv) {
    //      MovieDetailsDTO movieDetailsDTO = customModelMapper.MovieEntity2MovieDTO(movie);
    //      long externalMovieId = movie.getExternalId();
    //      if (externalMovieId != 0) {
    //        appendTheMovieDBData(movieDetailsDTO);
    //      }
    //
    //      movieDetailsDTOS.add(movieDetailsDTO);
    //    }
    //    return movieDetailsDTOS;


    Page<MovieDetails> movieDetails = movieRepository.findAll(pageRequest);
    if (movieDetails.getSize() == 0 || !movieDetails.hasContent()) {
      throw new NotFoundException("ERR_404");
    }

    List<MovieDetails> pagedThings = movieDetails.getContent();

    List<MovieDetailsDTO> movieDetailsDTOS = new ArrayList<>();


    for (MovieDetails movie : pagedThings) {
      MovieDetailsDTO movieDetailsDTO = customModelMapper.MovieEntity2MovieDTO(movie);
      long externalMovieId = movie.getExternalId();
      if (externalMovieId != 0) {
             appendTheMovieDBData(movieDetailsDTO);
      }
      movieDetailsDTOS.add(movieDetailsDTO);
    }

    List<MovieDetailsDTO> movieDetailsDTOList=
        movieDetails.getContent().stream()
            .map(movie -> transformMovieEntity(movie)).collect(Collectors.toList()


        );

  PageMovieDetailsDTO pagedMovieDetailsDTO= new PageMovieDetailsDTO(movieDetailsDTOList,

      movieDetails.getTotalElements(),movieDetails.getTotalPages());

//)
//
//       .collect(Collectors.toList());

 ;
    return pagedMovieDetailsDTO;

}


  /**
   * Delete movie information from database using primary key
   *
   * @param movieId primary key
   * @throws  NotFoundException if no movie matching movieId present in db.
   */
  public void deleteWatchedMovie(long movieId) {
    Optional<MovieDetails> movieDetails = movieRepository.findById(movieId);
    movieDetails.orElseThrow(() -> new NotFoundException("Not found"));

    movieRepository.delete(movieDetails.get());
  }

  /**
   * Helper function to append moviedb api data.
   * Extract ExternalId from object and use TheMovieDBService function to find out theMovieDB API data
   * @see TheMovieDBService
   * @param movieDetailsDTO
   * @return MovieDetailsDTO object with updated information from theMovieDB
   */
  public MovieDetailsDTO appendTheMovieDBData(MovieDetailsDTO movieDetailsDTO) {

    MovieDB movieDB = theMovieDBService.getMovieById(movieDetailsDTO.getExternalId());
    movieDetailsDTO.setOverView(movieDB.getMovieSummary());
    movieDetailsDTO.setImdbId(movieDB.getImdbId());
    movieDetailsDTO.setPosterPath(theMovieDBService.moviePosterPath(movieDB.getPosterPath()));
    movieDetailsDTO.setOriginalLanguage(movieDB.getOriginalLanguge());
    return movieDetailsDTO;
  }

  /**
   * Helper function to map Movie Entity to MovieDetails DTO and call appendTheMovieDBData function for enrichment
   *
   * @param movieDetails Entity object from db
   * @return DTO object with updated information.
   */
  public MovieDetailsDTO transformMovieEntity(MovieDetails movieDetails)
  {
    MovieDetailsDTO movieDetailsDTO=  customModelMapper.MovieEntity2MovieDTO(movieDetails);
    long externalMovieId = movieDetails.getExternalId();
    if (externalMovieId != 0) {
      appendTheMovieDBData(movieDetailsDTO);
      return  movieDetailsDTO;
    }
    return movieDetailsDTO;



  }
}
