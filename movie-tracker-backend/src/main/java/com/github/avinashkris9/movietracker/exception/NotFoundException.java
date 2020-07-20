package com.github.avinashkris9.movietracker.exception;

/**
 *
 * Runtime exception thrown when entity not found in db
 */
public class NotFoundException extends RuntimeException {


  public NotFoundException(String message) {

    super(message);

  }
}
