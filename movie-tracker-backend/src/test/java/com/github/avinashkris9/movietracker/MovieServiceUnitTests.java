package com.github.avinashkris9.movietracker;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.model.MovieDBResponse;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieResponse;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.service.TheMovieDBService;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
 class MovieServiceUnitTests {


  @Rule

  public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.LENIENT);


  @Mock
  private MovieRepository movieRepository;

  @Mock
  private  TheMovieDBService theMovieDBService;
  @Mock
  private  CustomModelMapper customModelMapper;
  @InjectMocks // inject the above created mocks to the service bean.
  private com.github.avinashkris9.movietracker.service.MovieService movieService;


  MovieDBDetails mockMovieDB ()
  {
    MovieDBResponse movieDBResponse =new MovieDBResponse();
    movieDBResponse.setMovieId(12L);
    movieDBResponse.setMovieSummary("Test movie");
    movieDBResponse.setImdbId("12");
    movieDBResponse.setPosterPath("/test");
    movieDBResponse.setTitle("Hello-World");


  List<MovieDBResponse> movieDBResponseList =new ArrayList<>();
  movieDBResponseList.add(movieDBResponse);
    MovieDBDetails movieDBDetails=new MovieDBDetails();
    movieDBDetails.setMovieDBResponseDetails(movieDBResponseList);
    return movieDBDetails;
  }

  MovieDetails initMovie()
 {

   MovieDetails movieDetails=new MovieDetails();
   movieDetails.setId(112L);
   movieDetails.setMovieName("Hello");
   movieDetails.setExternalId(12L);
    movieDetails.setNumberOfWatch(1);
    return movieDetails;

  }

  @Test
 void addShowWatchedSuccess()
  {
    MovieResponse movieResponse =new MovieResponse();
    movieResponse.setName("Hello");
    movieResponse.setExternalId(12);
    movieResponse.setRating(5);


    MovieDetails movieDetails=new MovieDetails();
    movieDetails.setId(112L);
    movieDetails.setMovieName("Hello");
    movieDetails.setExternalId(12L);
    movieDetails.setNumberOfWatch(1);


   lenient(). when(
        theMovieDBService.getMovieDetailsBySearch(anyString(),anyString()))
        .thenReturn(mockMovieDB());

    MovieDetails movieDetailsEntity=customModelMapper.movieDTO2MovieEntity(movieResponse);


   when(movieRepository.save(movieDetailsEntity)).thenReturn(movieDetails);



    when(customModelMapper.movieEntity2MovieDTO(Mockito.any(MovieDetails.class))).thenCallRealMethod();

    MovieResponse movieResponse1 =movieService.addShowWatched(movieResponse);


    Assertions.assertEquals(12L, movieResponse1.getExternalId());
  }

  @Test
  void getShowWatchedSuccess()
  {
    MovieResponse movieResponse =new MovieResponse();
    movieResponse.setName("Hello");
    movieResponse.setExternalId(12);
    movieResponse.setRating(5);

    MovieDetails movieDetails=new MovieDetails();
    movieDetails.setId(112L);
    movieDetails.setMovieName("Hello");
    movieDetails.setExternalId(12L);
    movieDetails.setNumberOfWatch(1);


    lenient(). when(
        theMovieDBService.getMovieDetailsBySearch(anyString(),anyString()))
        .thenReturn(mockMovieDB());

    MovieDetails movieDetailsEntity=customModelMapper.movieDTO2MovieEntity(movieResponse);


    when(movieRepository.findById(112L)).thenReturn(Optional.of(movieDetails));



    when(customModelMapper.movieEntity2MovieDTO(Mockito.any(MovieDetails.class))).thenCallRealMethod();

    MovieResponse movieResponse1 =movieService.getShowByShowId(112L);


    Assertions.assertEquals(12L, movieResponse1.getExternalId());
  }




}
