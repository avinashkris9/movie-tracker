package com.github.avinashkris9.movietracker.exception;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
    return (clientHttpResponse.getStatusCode().series() == CLIENT_ERROR
        || clientHttpResponse.getStatusCode().series() == SERVER_ERROR);
  }

  @Override
  public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
    if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
      // handle SERVER_ERROR
      throw new TheMovieDBException("Unable to connect movieDB");
    } else if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
      // handle CLIENT_ERROR
      if (clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("NOTFOUND IN MOVIEDB");
      }
    }
  }
}
