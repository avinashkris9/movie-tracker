package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.entity.TvDetails;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDBResponse;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieResponse;
import com.github.avinashkris9.movietracker.model.WatchListDTO;
import com.github.avinashkris9.movietracker.utils.APIUtils;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Business logics mainly to perform the external TheMovieDB API calls
 */
@Service
@Slf4j
public class TheMovieDBService {

  @Value(("${api.key}"))
  private  String apiKey;

  @Value(("${api.url}"))
  private String baseUrl;

  @Value("${api.url.imagedb}")
  private String imageUrl;
  // values are injected after constructor call. So we cannot do a concatenation here.
  private final String SEARCH_MOVIE_URI = "/search/movie";
  private  final String SEARCH_TV_URI = "/search/tv";

  private final String IMDB_URL="https://www.imdb.com/title";
  private final RestTemplate restTemplate;
  private final CustomModelMapper customModelMapper;


  @Autowired
  public TheMovieDBService(RestTemplate restTemplate, CustomModelMapper customModelMapper) {
    this.restTemplate = restTemplate;
    this.customModelMapper = customModelMapper;
  }


  /**
   * Generate url for movie search
   *
   * @param movieName searchkey name for movie
   * @return URI for the movie db
   */
  public URI generateMovieDBSearchUrl(String movieName, String showType) {

    String searchUrl = baseUrl;
    if (showType.equalsIgnoreCase(SHOW_TYPES.MOVIE.name())) {
      searchUrl = searchUrl.concat(SEARCH_MOVIE_URI);
    } else if (showType.equalsIgnoreCase(SHOW_TYPES.TV.name())) {
      searchUrl = searchUrl.concat(SEARCH_TV_URI);
    } else {
      log.error(" Invalid SHOW TYPE");
    }

    Map<String, String> params = new HashMap<>();
    params.put("query", movieName);
    params.put("api_key", apiKey);
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(searchUrl);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.queryParam(entry.getKey(), entry.getValue());
    }

    /*
     * A small note on encoding the url. So if a movie name contain space, it needs to be encoded to
     * %20 or + on url. If we use builder.toString we would need to encode it manually. If we use
     * builder.toURI , encoding will happen using RFC standard.
     */
    URI uri = builder.encode().build().toUri();
    log.info(" Movie DB Search Url {}", uri.toString());
    return uri;
  }


  /**
   * Create movie id url
   *
   * @param id id of movie/tv
   * @return URI for themoviedb api
   * @throws URISyntaxException exception of no proper uri formed
   */
  public URI movieIdUrl(long id, String showType) throws URISyntaxException {

    //TODO - this can be removed entirely by using enum values.
    if (showType.equalsIgnoreCase(SHOW_TYPES.MOVIE.name())) {
      showType = "movie";
    } else if (showType.equalsIgnoreCase(SHOW_TYPES.TV.name())) {
      showType = "tv";
    } else {
      log.error(" Invalid SHOW TYPE");
    }
    String url = baseUrl + "/" + showType + "/" + id + "?api_key=" + apiKey;
    log.debug(url);
    return new URI(url);
  }


  /**
   * Search a movie in movie db
   *
   * @param movieName name of movie
   * @return MovieDBDetails object with movie information
   */
  public MovieDBDetails getMovieDetailsBySearch(String movieName, String showType) {

    URI uri = generateMovieDBSearchUrl(movieName, showType);
    log.info("Checking movie db for data {} on {}", movieName, uri);
    MovieDBDetails movieDBDetails =new MovieDBDetails();
    movieDBDetails=restTemplate.getForObject(uri, MovieDBDetails.class);
    if (movieDBDetails.getMovieDBResponseDetails().isEmpty()) {
      log.error(" No movie info present for {} " + movieName);

    }
    log.debug(movieDBDetails.toString());
    return movieDBDetails;
  }

  /**
   * Find movie by movie id in moviedb
   *
   * @param movieId id primary key for movie
   * @return MovieDB object with moviedb data
   */
  public MovieDBResponse getMovieById(long movieId, String showType) {

    MovieDBResponse movieDBResponse =new MovieDBResponse();

    log.info(" Querying data from themoviedb");
    URI uri = null;
    try {
      uri = movieIdUrl(movieId, showType);
      movieDBResponse = restTemplate.getForObject(uri, MovieDBResponse.class);
      if (Objects.isNull(movieDBResponse)) {
        log.info(" No movie found in movieDB for id {}", movieId);
        throw new NotFoundException("ERR_NOT IN MOVIE_DB");
      }
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
    catch (Exception e) {

      log.error(" Unknown Exception !",e);
      log.error(e.getMessage());
    }
    return movieDBResponse;



  }


  /**
   * Helper function to generate themovie db image url
   *
   * @param fileName movieDB posterpath file name
   * @return imageurl string for poster
   */
  public String moviePosterPath(String fileName) {
    return this.imageUrl.concat("/").concat(fileName);
  }


  /**
   * Helper function to append moviedb api data. Extract ExternalId from object and use
   * TheMovieDBService function to find out theMovieDB API data
   *
   * @param movieResponse movie data
   * @return MovieDetailsDTO object with updated information from theMovieDB
   */
  public MovieResponse appendTheMovieDBData(MovieResponse movieResponse, String showType) {


    MovieDBResponse movieDBResponse = getMovieById(movieResponse.getExternalId(), showType);

    if(!Objects.isNull(movieDBResponse.getMovieId()))
    {

      log.debug(movieDBResponse.toString());
      movieResponse.setOverView(movieDBResponse.getMovieSummary());
      movieResponse.setImdbId( IMDB_URL+"/"+(movieDBResponse.getImdbId()));
     if(!APIUtils.isNullOrEmpty(movieDBResponse.getPosterPath()))
     {

       movieResponse.setPosterPath(moviePosterPath(movieDBResponse.getPosterPath()));
     }
      movieResponse.setOriginalLanguage(movieDBResponse.getOriginalLanguge());
      movieResponse.setReleaseDate(movieDBResponse.getReleaseDate());
    }
    else
    {
      log.error(" No info from the movie db !!!!!!!!!!!!!!!!!!!!!!!!");
    }

    return movieResponse;
  }

  public WatchListDTO appendTheMovieDBDataToWatchList(WatchListDTO watchListDTO, String showType) {

    MovieDBResponse movieDBResponse = getMovieById(watchListDTO.getExternalId(), showType);
    watchListDTO.setOverView(movieDBResponse.getMovieSummary());
    watchListDTO.setImdbId(movieDBResponse.getImdbId());
    watchListDTO.setPosterPath(moviePosterPath(movieDBResponse.getPosterPath()));
    watchListDTO.setOriginalLanguage(movieDBResponse.getOriginalLanguge());
    return watchListDTO;
  }
  /**
   * Helper function to map Movie Entity to MovieDetails DTO and call appendTheMovieDBData function
   * for enrichment
   *
   * @param movieDetails Entity object from db
   * @return DTO object with updated information.
   */
  public MovieResponse transformMovieEntity(MovieDetails movieDetails) {
    MovieResponse movieResponse = customModelMapper.movieEntity2MovieDTO(movieDetails);
    log.info(movieResponse.toString());
    long externalMovieId = movieDetails.getExternalId();
    if (externalMovieId != 0) {
      appendTheMovieDBData(movieResponse, SHOW_TYPES.MOVIE.toString());
      return movieResponse;
    }
    return movieResponse;

  }

  /**
   * Helper function to map Tv Entity to MovieDetails DTO and call appendTheMovieDBData function for
   * enrichment
   *
   * @param tvDetails Entity object from db
   * @return DTO object with updated information.
   */
  public MovieResponse transformTvEntity(TvDetails tvDetails) {
    MovieResponse movieResponse = customModelMapper.tvEntity2MovieDTO(tvDetails);
    log.debug(" Performing TheMovieDB Query for {} with id {} ", tvDetails.getTvShowName(),
        tvDetails.getExternalId());
    long externalMovieId = tvDetails.getExternalId();
    if (externalMovieId != 0) {
      appendTheMovieDBData(movieResponse, SHOW_TYPES.TV.toString());
      return movieResponse;
    }
    return movieResponse;

  }
}
