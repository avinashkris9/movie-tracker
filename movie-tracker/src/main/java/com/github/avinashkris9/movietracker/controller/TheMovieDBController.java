package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDB;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.service.TheMovieDBService;
import java.net.URISyntaxException;
import java.util.Objects;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/themoviedb")
public class TheMovieDBController {

  private final RestTemplate restTemplate;
  private final TheMovieDBService theMovieDBService;

  public TheMovieDBController(RestTemplate restTemplate, TheMovieDBService theMovieDBService) {
    this.restTemplate = restTemplate;
    this.theMovieDBService = theMovieDBService;
  }

  @RequestMapping("/{movieId}")
  public MovieDB getMovieDetails(@PathVariable long movieId) throws URISyntaxException {

    //    String url= APIUtils.baseUrl +"/movie/"+movieId+"?api_key="+APIUtils.baseUrl;
    //    System.out.println(url);

    MovieDB movieDB =
        restTemplate.getForObject(theMovieDBService.movieIdUrl(movieId), MovieDB.class);

    if (Objects.isNull(movieDB)) {
      throw new NotFoundException("MovieDB entry not found");
    }
    return movieDB;
  }

  @GetMapping("/search")
  public MovieDBDetails searchMovieDB(@RequestParam("name") String movieName) {

    MovieDBDetails movieDBDetails =
        restTemplate.getForObject(
            theMovieDBService.generateMovieDBSearchUrl(movieName), MovieDBDetails.class);

    movieDBDetails
        .getMovieDBDetails()
        .forEach(
            f -> {
              if (f.getPosterPath() != null) {
                f.setPosterPath(theMovieDBService.moviePosterPath(f.getPosterPath()));
              }
            });

    //    ResponseEntity<String> responseEntity
    //        =restTemplate.getForEntity(uri,String.class);
    //
    //    System.out.println(responseEntity);
    return movieDBDetails;
  }
}
