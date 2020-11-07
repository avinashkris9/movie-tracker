package com.github.avinashkris9.movietracker;

import com.github.avinashkris9.movietracker.exception.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *
 * Spring boot app for tracking movies/tvshows
 * @Author Avinash Krishnan
 * 
 */
@SpringBootApplication
public class MovieTrackerApplication {



  public static void main(String[] args) {

    SpringApplication springApplication = new SpringApplication(MovieTrackerApplication.class);

    springApplication.setBannerMode(Banner.Mode.OFF);
    springApplication.run(args);

  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {

    return builder.errorHandler(new RestTemplateResponseErrorHandler()).build();
  }
}
