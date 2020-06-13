package com.github.avinashkris9.movietracker.exception;

public class EntityExistsException extends RuntimeException {

  /**
   * Exception thrown when attempt to reinsert existing entity to db
   * @param exception
   */

  public EntityExistsException(String exception) {

    super(exception);
  }
}
