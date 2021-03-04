package com.github.avinashkris9.movietracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.model.MovieResponse;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.service.MovieService;
import com.github.avinashkris9.movietracker.utils.APIUtils.API_CODES;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieIntegrationTests {


  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private    CustomModelMapper customModelMapper;
  @Autowired
  private MovieService movieService;
  @Autowired
  private MovieRepository movieRepository;

  MovieResponse movieResponse =new MovieResponse();

  @Test@Order(1)
  @Transactional
  void addNewMovieSuccess() throws Exception {

    //movieRepository.deleteById(1763L);
    MovieResponse movieResponse =new MovieResponse();
    movieResponse.setName("Hello");
    movieResponse.setRating(4);


    mockMvc.perform(post("/api/movies" )
        .contentType("application/json")

        .content(objectMapper.writeValueAsString(movieResponse)))
        .andExpect(status().isOk());

    MovieDetails userEntity = movieRepository.findByMovieName("Hello");
    System.out.println(userEntity);
    assertThat(userEntity.getMovieName()=="Hello");

    this.movieResponse =customModelMapper.movieEntity2MovieDTO(movieRepository.findByMovieName("Hello"));

  }

  @Test()
  @Order(2)
  void getAllMovieDetailsSuccess() throws Exception
  {
    String allMovies=mockMvc.perform(get("/api/movies")).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    System.out.println(allMovies);
    assertThat(allMovies.contains("Hello"));
  }
  @Test()
  @Order(3)
  void getMovieByIdSuccessTest() throws Exception
  {
    String movie=mockMvc.perform(get("/api/movies/{movieId}",1764L)).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    System.out.println(movie);
    assertThat(movie.contains("Hello"));
  }

  @Test()
  @Order(4)
  void getMovieByIdFailedTest() throws Exception
  {
  //  movieRepository.deleteById(176211111111111111L);
    String movie=mockMvc.perform(get("/api/movies/"+"176211111111111111L")).andExpect(status().isNotFound())
        .andReturn().getResponse().getContentAsString();

    assertThat(movie.contains(API_CODES.NOT_FOUND.name()));


  }

  @Test()
  @Order(5)
  void updateMovieSuccess() throws Exception
  {


    this.movieResponse.setNumberOfWatch(12);
    String movie=mockMvc.perform(put("/api/movies/{movieId}",this.movieResponse.getId())).andExpect(status().isNotFound())
        .andReturn().getResponse().getContentAsString();

    System.out.println(movie);
    assertThat(movie.contains("12"));


  }
  @Test()
  @Order(7)
  void deleteMovie() throws Exception
  {


    this.movieResponse.setNumberOfWatch(12);
    mockMvc.perform(delete("/api/movies/{movieId}",1764)).andExpect(status().isOk())
        .andReturn();




  }
}
