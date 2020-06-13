package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDB;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Business logics mainly to perform the external TheMovieDB API calls
 */
@Service
public class TheMovieDBService {

  private final Logger logger = LoggerFactory.getLogger(TheMovieDBService.class);
  private final RestTemplate restTemplate;

  @Value(("${api.key}"))
  private String apiKey;

  @Value(("${api.url}"))
  private String baseUrl;

  @Value("${api.url.imagedb}")
  private String imageUrl;
  // values are injected after constructor call. So we cannot do a concatenation here.
  private String searchUri = "/search/movie";

  @Autowired
  public TheMovieDBService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }



  /**
   * Generate url for movie search
   * @param movieName
   * @return URI for the movie db
   */
  public URI generateMovieDBSearchUrl(String movieName) {

    String searchUrl = baseUrl + searchUri;
    Map<String, String> params = new HashMap<>();
    params.put("query", movieName);
    params.put("api_key", apiKey);
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(searchUrl);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.queryParam(entry.getKey(), entry.getValue());
    }
    /**
     * A small note on encoding the url. So if a movie name contain space, it needs to be encoded to
     * %20 or + on url. If we use builder.toString we would need to encode it manually. If we use
     * builder.toURI , encoding will happen using RFC standard.
     */
    URI uri = builder.encode().build().toUri();
    return uri;
  }


  /**
   * Create movie id url
   * @param movieId
   * @return
   * @throws URISyntaxException
   */
  public URI movieIdUrl(long movieId) throws URISyntaxException {

    String url = baseUrl + "/movie/" + movieId + "?api_key=" + apiKey;
    URI uri = new URI(url);

    return uri;
  }


  /**
   * Search a movie in movie db
   * @param movieName
   * @return
   */
  public MovieDBDetails getMovieDetailsBySearch(String movieName) {

    URI uri = generateMovieDBSearchUrl(movieName);
    logger.info("Checking movie db for data {} on {}", movieName, uri);
    MovieDBDetails movieDBDetails =
        restTemplate.getForObject(generateMovieDBSearchUrl(movieName), MovieDBDetails.class);

    if (movieDBDetails.getMovieDBDetails().isEmpty()) {
      logger.error(" No movie info present for {} " + movieName);
    }


    return movieDBDetails;
  }

  /**
   * Find movie by movie id in moviedb
   * @param movieId
   * @return
   */
  public MovieDB getMovieById(long movieId) {

    MovieDB movieDB = new MovieDB();
    try {

      URI uri = movieIdUrl(movieId);

      movieDB = restTemplate.getForObject(uri, MovieDB.class);

      if (Objects.isNull(movieDB)) {
        logger.info(" No movie found in movieDB for id {}", movieId);
        throw new NotFoundException("ERR_NOT IN MOVIE_DB");
      }
      ;
      return movieDB;
    } catch (Exception e) {
      logger.error("********* Exception *********");
      logger.error(e.getMessage());

    }
    return movieDB;
  }


  /**
   * Helper function to generate themovie db image url
   * @param fileName
   * @return
   */
  public String moviePosterPath(String fileName) {
    System.out.println(fileName);
    return this.imageUrl.concat("/").concat(fileName);
  }
}
