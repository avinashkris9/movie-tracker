package com.github.avinashkris9.movietracker;

import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.service.MovieService;
import com.github.avinashkris9.movietracker.service.TheMovieDBService;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MovieServiceTests {


  @InjectMocks
  private MovieService movieService;

  @Mock
  private  MovieRepository movieRepository;
  @Mock
  private  TheMovieDBService theMovieDBService;
  @Mock
  private  CustomModelMapper customModelMapper;


}
