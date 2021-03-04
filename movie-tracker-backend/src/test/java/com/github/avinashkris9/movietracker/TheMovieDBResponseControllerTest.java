package com.github.avinashkris9.movietracker;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.avinashkris9.movietracker.controller.TheMovieDBController;
import com.github.avinashkris9.movietracker.exception.RestExceptionHandler;
import com.github.avinashkris9.movietracker.exception.RestTemplateResponseErrorHandler;
import com.github.avinashkris9.movietracker.service.MovieService;
import com.github.avinashkris9.movietracker.service.TheMovieDBService;
import com.github.avinashkris9.movietracker.utils.APIUtils;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.time.Duration;
import org.aspectj.lang.annotation.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class) // no need on junit5
@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration(classes = { TheMovieDBController.class,TheMovieDBService.class,RestTemplate.class, RestTemplateResponseErrorHandler.class,Exception.class,    RestExceptionHandler.class,   CustomModelMapper.class})

//@WebAppConfiguration

class TheMovieDBResponseControllerTest {



//  @Autowired
//  RestTemplate restTemplate  =new RestTemplateBuilder().build();;
  @Autowired
  private  MockMvc mockMvc;

  @BeforeTestMethod()

  @Test
   void testMovieSearch() throws Exception {

    searchMovieDBforMovieTest(   SHOW_TYPES.MOVIE.name(),"Narasim");
    searchMovieDBforTvTest(SHOW_TYPES.TV.name(), "Big Bang");

  }

  @Test
  void testMovieSearch404()throws Exception
  {
    testMovieSearch404 (SHOW_TYPES.MOVIE.name(),"viiii");
  }


  private void searchMovieDBforMovieTest(String showType,String name) throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/themoviedb/search").
        queryParam("showType",showType)
        .queryParam("name",name)


    .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    String resultDOW = result.getResponse().getContentAsString();

    assertNotNull(resultDOW);
  //  assetCo(dow, resultDOW);
  }
 void testMovieSearch404(String showType,String name) throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/themoviedb/search").
        queryParam("showType",showType)
        .queryParam("name",name)


        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

   // String resultDOW = result.getResponse().getContentAsString();

    //  assetCo(dow, resultDOW);
  }


  private void searchMovieDBforTvTest(String showType,String name) throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/themoviedb/search").
        queryParam("showType",showType)
        .queryParam("name",name)


        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    String resultDOW = result.getResponse().getContentAsString();
    System.out.println(resultDOW);
    assertNotNull(resultDOW);
    //  assetCo(dow, resultDOW);
  }

  @Test
  public void getMovieDetailsTest() throws Exception {
    MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get
        ("/api/themoviedb/movies/"+137265)).andExpect(status().isOk())
        .andReturn();
  String movieData = result.getResponse().getContentAsString();
//    System.out.println(resultDOW);

    assertNotNull(movieData);

  }

  @Test()
  public void getMovieDetailsTest404() throws Exception {

    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/themoviedb/movies/" + 1L))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andExpect(status().isNotFound())
            .andReturn();
  String output= result.getResponse().getContentAsString();

   assertThat( output.contains(APIUtils.API_CODES.NOT_FOUND.name()));


  }

}
