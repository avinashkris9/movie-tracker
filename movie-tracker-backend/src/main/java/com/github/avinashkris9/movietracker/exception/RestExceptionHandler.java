package com.github.avinashkris9.movietracker.exception;

import com.github.avinashkris9.movietracker.model.APIError;
import com.github.avinashkris9.movietracker.utils.ApiCodes;
import com.github.avinashkris9.movietracker.utils.ApiCodes.API_CODES;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * Spring controller advice class for all rest exceptions.
 *  All exceptions response body are wrapped on APIError bean object
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    APIError apiError = new APIError();
    apiError.setCode(" todo");
    apiError.setMessage(ex.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


    List<String> mandatoryFieldsMissing=
        ex.getBindingResult().getFieldErrors().stream().
           map(FieldError::getDefaultMessage).collect(Collectors.toList());
    APIError apiError=new APIError();
    if(mandatoryFieldsMissing.isEmpty())
    {

      apiError.setMessage((ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage()));
    }
    else
    {
      logger.debug(mandatoryFieldsMissing);
      String errorMessage=mandatoryFieldsMissing.stream().collect(Collectors.joining(",","{","}"));
      errorMessage=errorMessage;
      apiError.setMessage(errorMessage);
    }




    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }
  /**
   * Rest exception response for NotFound Exception
   * @param exception
   * @return
   */
  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<APIError> handleNotFoundException(Exception exception) {


    APIError apiError = new APIError();
    apiError.setCode(API_CODES.NOT_FOUND.name());
    apiError.setMessage(exception.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }


  /**
   * Rest exception response for EntityExists Exception
   * @param exception
   * @return
   */
  @ExceptionHandler(value = EntityExistsException.class)
  public ResponseEntity<APIError> EntityExistsExceptionHandler(Exception exception) {

    APIError apiError = new APIError();
    apiError.setCode(API_CODES.DUPLICATE.name());
    apiError.setMessage(exception.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle TheMovieDB Exception
   * @param exception
   * @return
   */
  @ExceptionHandler(value = TheMovieDBException.class)
  public ResponseEntity<APIError> TheMovieDBExceptionHandler(TheMovieDBException exception) {

    APIError apiError = new APIError();
    apiError.setCode(API_CODES.NOT_FOUND.name());
    apiError.setMessage(exception.getMessage());
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
