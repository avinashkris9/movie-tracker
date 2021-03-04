package com.github.avinashkris9.movietracker;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.model.MovieDB;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.service.MovieService;
import com.github.avinashkris9.movietracker.service.TheMovieDBService;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
    MovieDB movieDB=new MovieDB();
    movieDB.setMovieId(12L);
    movieDB.setMovieSummary("Test movie");
    movieDB.setImdbId("12");
    movieDB.setPosterPath("/test");
    movieDB.setTitle("Hello-World");


  List<MovieDB> movieDBList=new ArrayList<>();
  movieDBList.add(movieDB);
    MovieDBDetails movieDBDetails=new MovieDBDetails();
    movieDBDetails.setMovieDBDetails(movieDBList);
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
    MovieDetailsDTO movieDetailsDTO=new MovieDetailsDTO();
    movieDetailsDTO.setName("Hello");
    movieDetailsDTO.setExternalId(12);
    movieDetailsDTO.setRating(5);


    MovieDetails movieDetails=new MovieDetails();
    movieDetails.setId(112L);
    movieDetails.setMovieName("Hello");
    movieDetails.setExternalId(12L);
    movieDetails.setNumberOfWatch(1);


   lenient(). when(
        theMovieDBService.getMovieDetailsBySearch(anyString(),anyString()))
        .thenReturn(mockMovieDB());

    MovieDetails movieDetailsEntity=customModelMapper.movieDTO2MovieEntity(movieDetailsDTO);


   when(movieRepository.save(movieDetailsEntity)).thenReturn(movieDetails);



    when(customModelMapper.movieEntity2MovieDTO(Mockito.any(MovieDetails.class))).thenCallRealMethod();

    MovieDetailsDTO movieDetailsDTO1=movieService.addShowWatched(movieDetailsDTO);


    Assertions.assertEquals(12L,movieDetailsDTO1.getExternalId());
  }

  @Test
  void getShowWatchedSuccess()
  {
    MovieDetailsDTO movieDetailsDTO=new MovieDetailsDTO();
    movieDetailsDTO.setName("Hello");
    movieDetailsDTO.setExternalId(12);
    movieDetailsDTO.setRating(5);

    MovieDetails movieDetails=new MovieDetails();
    movieDetails.setId(112L);
    movieDetails.setMovieName("Hello");
    movieDetails.setExternalId(12L);
    movieDetails.setNumberOfWatch(1);


    lenient(). when(
        theMovieDBService.getMovieDetailsBySearch(anyString(),anyString()))
        .thenReturn(mockMovieDB());

    MovieDetails movieDetailsEntity=customModelMapper.movieDTO2MovieEntity(movieDetailsDTO);


    when(movieRepository.findById(112L)).thenReturn(Optional.of(movieDetails));



    when(customModelMapper.movieEntity2MovieDTO(Mockito.any(MovieDetails.class))).thenCallRealMethod();

    MovieDetailsDTO movieDetailsDTO1=movieService.getShowByShowId(112L);


    Assertions.assertEquals(12L,movieDetailsDTO1.getExternalId());
  }




}
