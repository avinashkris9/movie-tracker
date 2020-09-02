package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.entity.TvDetails;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDB;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.model.WatchListDTO;
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
  private final String searchMovieUri = "/search/movie";
  private  final String searchTvUri = "/search/tv";

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
      searchUrl = searchUrl.concat(searchMovieUri);
    } else if (showType.equalsIgnoreCase(SHOW_TYPES.TV.name())) {
      searchUrl = searchUrl.concat(searchTvUri);
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
    MovieDBDetails movieDBDetails =
        restTemplate.getForObject(uri, MovieDBDetails.class);
    if (movieDBDetails.getMovieDBDetails().isEmpty()) {
      log.error(" No movie info present for {} " + movieName);
      return null;//bad
    }

    return movieDBDetails;
  }

  /**
   * Find movie by movie id in moviedb
   *
   * @param movieId id primary key for movie
   * @return MovieDB object with moviedb data
   */
  public MovieDB getMovieById(long movieId, String showType) {

    MovieDB movieDB=new MovieDB();

    URI uri = null;
    try {
      uri = movieIdUrl(movieId, showType);
      movieDB = restTemplate.getForObject(uri, MovieDB.class);
      if (Objects.isNull(movieDB)) {
        log.info(" No movie found in movieDB for id {}", movieId);
        throw new NotFoundException("ERR_NOT IN MOVIE_DB");
      }
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
    catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
      return movieDB;


    
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
   * @param movieDetailsDTO movie data
   * @return MovieDetailsDTO object with updated information from theMovieDB
   */
  public MovieDetailsDTO appendTheMovieDBData(MovieDetailsDTO movieDetailsDTO, String showType) {

    MovieDB movieDB = getMovieById(movieDetailsDTO.getExternalId(), showType);
    movieDetailsDTO.setOverView(movieDB.getMovieSummary());
    movieDetailsDTO.setImdbId(movieDB.getImdbId());
    movieDetailsDTO.setPosterPath(moviePosterPath(movieDB.getPosterPath()));
    movieDetailsDTO.setOriginalLanguage(movieDB.getOriginalLanguge());
    return movieDetailsDTO;
  }

  public WatchListDTO appendTheMovieDBDataToWatchList(WatchListDTO watchListDTO, String showType) {

    MovieDB movieDB = getMovieById(watchListDTO.getExternalId(), showType);
    watchListDTO.setOverView(movieDB.getMovieSummary());
    watchListDTO.setImdbId(movieDB.getImdbId());
    watchListDTO.setPosterPath(moviePosterPath(movieDB.getPosterPath()));
    watchListDTO.setOriginalLanguage(movieDB.getOriginalLanguge());
    return watchListDTO;
  }
  /**
   * Helper function to map Movie Entity to MovieDetails DTO and call appendTheMovieDBData function
   * for enrichment
   *
   * @param movieDetails Entity object from db
   * @return DTO object with updated information.
   */
  public MovieDetailsDTO transformMovieEntity(MovieDetails movieDetails) {
    MovieDetailsDTO movieDetailsDTO = customModelMapper.movieEntity2MovieDTO(movieDetails);
    log.info(movieDetailsDTO.toString());
    long externalMovieId = movieDetails.getExternalId();
    if (externalMovieId != 0) {
      appendTheMovieDBData(movieDetailsDTO, SHOW_TYPES.MOVIE.toString());
      return movieDetailsDTO;
    }
    return movieDetailsDTO;

  }

  /**
   * Helper function to map Tv Entity to MovieDetails DTO and call appendTheMovieDBData function for
   * enrichment
   *
   * @param tvDetails Entity object from db
   * @return DTO object with updated information.
   */
  public MovieDetailsDTO transformTvEntity(TvDetails tvDetails) {
    MovieDetailsDTO movieDetailsDTO = customModelMapper.tvEntity2MovieDTO(tvDetails);
    log.debug(" Performing TheMovieDB Query for {} with id {} ", tvDetails.getTvShowName(),
        tvDetails.getExternalId());
    long externalMovieId = tvDetails.getExternalId();
    if (externalMovieId != 0) {
      appendTheMovieDBData(movieDetailsDTO, SHOW_TYPES.TV.toString());
      return movieDetailsDTO;
    }
    return movieDetailsDTO;

  }
}
