package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.TvDetails;
import com.github.avinashkris9.movietracker.entity.TvReview;
import com.github.avinashkris9.movietracker.exception.EntityExistsException;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieResponse;
import com.github.avinashkris9.movietracker.model.PageMovieResponse;
import com.github.avinashkris9.movietracker.repository.TvRepository;
import com.github.avinashkris9.movietracker.utils.APIUtils;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TvService implements ShowManagementService<MovieResponse, PageMovieResponse> {

  private final TvRepository tvRepository;
  private final TheMovieDBService theMovieDBService;
  private final CustomModelMapper customModelMapper;
  // initialise log.

  public TvService(TvRepository tvRepository,
      TheMovieDBService theMovieDBService,
      CustomModelMapper customModelMapper) {
    this.tvRepository = tvRepository;
    this.theMovieDBService = theMovieDBService;
    this.customModelMapper = customModelMapper;
  }

  /**
   * Provide all TV shoes Details in database as paginated data
   *
   * @param pageRequest Pageable object with page number and page size
   * @return PageMovieDetailsDTO object holding list of movies  and paging information
   * @throws NotFoundException if no movies found in database
   */
  public PageMovieResponse getAllShowsWatched(Pageable pageRequest) {

    Page<TvDetails> tvShowDetails = tvRepository.findAll(pageRequest);
    //throw exception if there are no movies
    // @TODO -> use enum for error code
    if (tvShowDetails.getSize() == 0 || !tvShowDetails.hasContent()) {
      throw new NotFoundException(APIUtils.API_CODES.NOT_FOUND.name());
    }

    List<MovieResponse> movieResponseList =
        tvShowDetails.getContent().stream()
            .map(theMovieDBService::transformTvEntity).collect(Collectors.toList()

        );

    return new PageMovieResponse(movieResponseList,

        tvShowDetails.getTotalElements(), tvShowDetails.getTotalPages());

  }

  /**
   * Search or query using tv show name Allows partial search using name
   *
   * @return List of all movies matching the movie name
   * @throws NotFoundException if no movies are found for the search string
   */
  public List<MovieResponse> getShowByShowName(String movieName) {
    List<TvDetails> tvShows = tvRepository.findByTvShowNameContainsIgnoreCase(movieName);


    if (tvShows.isEmpty()) {
      throw new NotFoundException(APIUtils.API_CODES.NOT_FOUND.name());
    }
    List<MovieResponse> movieResponses = new ArrayList<>();
    for (TvDetails md : tvShows) {
      MovieResponse movieResponse = customModelMapper.tvEntity2MovieDTO(md);
      theMovieDBService.appendTheMovieDBData(movieResponse, SHOW_TYPES.TV.name());
      movieResponses.add(movieResponse);
    }
    return movieResponses;
  }

  /**
   * Retrive TV Show details from db using the  id TheMovieDB data is appended if externalId field
   * holds the TheMovieDB id
   *
   * @param tvId Primary Key id for movie
   * @return DTO object for movie details
   * @throws NotFoundException if no entry found for movieId
   */
  public MovieResponse getShowByShowId(long tvId) {
    Optional<TvDetails> tvDetails = tvRepository.findById(tvId);

    if (tvDetails.isPresent()) {

      MovieResponse movieResponse = customModelMapper.tvEntity2MovieDTO(tvDetails.get());
      long externalId = tvDetails.get().getExternalId();
      if (externalId != 0) {
        theMovieDBService.appendTheMovieDBData(movieResponse, SHOW_TYPES.TV.name());
      }

      log.debug(movieResponse.toString());
      return movieResponse;
    }
    throw new NotFoundException(APIUtils.API_CODES.NOT_FOUND.name());
  }


  /**
   * Inserts TV details to db. TheMovieDB API is being called with the movie name to perform
   * additional enrichment. Sets default value for LastWatched  fields if not present.
   *
   * @param tvDetails DTO object
   * @return DTO object with extra enrichment information
   * @throws EntityExistsException if same movie name present in database.
   */
  public MovieResponse addShowWatched(MovieResponse tvDetails) {

    if (Objects.isNull(tvDetails.getLastWatched())) {
      log.info("No watched date provided so setting today's date");
      tvDetails.setLastWatched(LocalDate.now());
    }

    //if external id is already populated. Trust the client.
    if (tvDetails.getExternalId() == 0) {
      TvDetails tvShowDetailsFromDb = tvRepository.findByTvShowName(tvDetails.getName());
      if (!Objects.isNull(tvShowDetailsFromDb)) {
        log.error("Movie {} exists in db {}", tvShowDetailsFromDb.getTvShowName(),
            tvShowDetailsFromDb.getId());
        throw new EntityExistsException(APIUtils.API_CODES.DUPLICATE.name());
      }
      // call themoviedb api and find out the movie ID.
      // TODO , find a better solution rather than using search api.
      MovieDBDetails optionalMovieDBDetails =
          theMovieDBService
              .getMovieDetailsBySearch(tvDetails.getName(), SHOW_TYPES.TV.name());
      if (!optionalMovieDBDetails.getMovieDBResponseDetails().isEmpty()) {
        long themovieDBMovieId = optionalMovieDBDetails.getMovieDBResponseDetails().get(0).getMovieId();
        log.debug("The movie db entry found with external id {} ", themovieDBMovieId);
        tvDetails.setExternalId(themovieDBMovieId);
      }
    }

    TvDetails tvDetailsEntity =customModelMapper.movieDetailsDTO2TvEntity(tvDetails);
    if(!(tvDetails.getReview() ==null || tvDetails.getReview().isEmpty()))
    {
      TvReview movieReview=new TvReview();
      movieReview.setTvDetails(tvDetailsEntity);
      movieReview.setLastReviewed(tvDetailsEntity.getLastWatched());
      movieReview.setReview(tvDetails.getReview());
//      movieDetails1.getMovieReviews().add(movieReview);
      tvDetailsEntity.addReview(movieReview);

    }

    TvDetails tvDetailsDAO = tvRepository
        .save(tvDetailsEntity);
    log.info("Sasa {}", tvDetailsDAO);
    tvDetails.setId(tvDetailsDAO.getId());
    return tvDetails;
  }


  /**
   * Update database with the new information received
   *
   * @param tvDetails DTO object for movie
   * @param tvId      primary key to identify database entry
   * @return MovieDetailsDTO DTO object
   */
  public MovieResponse updateShowWatched(MovieResponse tvDetails, long tvId) {

    Optional<TvDetails> tvShowFromDb = tvRepository.findById(tvId);
    LocalDate today = LocalDate.now();
    if (!tvShowFromDb.isPresent()) {

      throw new NotFoundException(APIUtils.TV_NOT_FOUND);
    }

    TvDetails tvDetailsEntity=tvShowFromDb.get();
    if (Objects.isNull(tvDetails.getLastWatched())) {
      log.info("No date provided. setting LastWatched data as today");
      tvDetails.setLastWatched(today);
    } else {
      //if data is older than what's in db. Don't replace it.
      if (tvDetails.getLastWatched().isBefore(tvShowFromDb.get().getLastWatched())) {
        tvDetails.setLastWatched(tvShowFromDb.get().getLastWatched());
      }
    }

    log.info("update movie details {}", tvDetails);
    tvDetailsEntity.setLastWatched(tvDetails.getLastWatched());

       if(!(tvDetails.getReview() ==null || tvDetails.getReview().isEmpty()))
    {
      TvReview tvReview=new TvReview();
      tvReview.setTvDetails(tvDetailsEntity);
      tvReview.setLastReviewed(tvDetailsEntity.getLastWatched());
      tvReview.setReview(tvDetails.getReview());
//      movieDetails1.getMovieReviews().add(tvReview);
      tvDetailsEntity.addReview(tvReview);

    }


    return customModelMapper.tvEntity2MovieDTO(   tvRepository.save(tvDetailsEntity));
  }
  /**
   * Delete TV information from database using primary key
   *
   * @param tvId primary key
   * @throws  NotFoundException if no movie matching tvId present in db.
   */
  public void deleteShowWatched(long tvId) {
    Optional<TvDetails> tvDetails = tvRepository.findById(tvId);
    tvDetails.orElseThrow(() -> new NotFoundException(APIUtils.TV_NOT_FOUND));
    tvRepository.deleteById(tvId);

  }

  public long getTvCount()
  {
    return tvRepository.count();
  }

  public long getTopRatedTvCount()
  {
    return tvRepository.countByRating(5);
  }


  public List<MovieResponse> getDumps()
  {
    List<TvDetails> movieDetails= tvRepository.findAll();
    if(movieDetails.isEmpty())
    {

      throw new NotFoundException("ERR_404");
    }

    List<MovieResponse> movieResponseList =
        movieDetails.stream()
            .map(
                x-> customModelMapper.tvEntity2MovieDTO(x))
            .collect(Collectors.toList());
    return movieResponseList;
  }
}
