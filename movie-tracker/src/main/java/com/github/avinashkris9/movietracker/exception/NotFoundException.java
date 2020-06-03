package com.github.avinashkris9.movietracker.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {

    super(message);
    System.out.println(" am i being called ?");
  }
}
