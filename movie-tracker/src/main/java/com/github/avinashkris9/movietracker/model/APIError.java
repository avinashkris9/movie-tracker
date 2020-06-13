package com.github.avinashkris9.movietracker.model;

import java.time.LocalDateTime;

public class APIError {

  private String message;
  private String code;
  private String timestamp;

  public APIError(String message, String code) {
    super();
    this.message = message;
    this.code = code;
    this.timestamp = LocalDateTime.now().toString();
  }

  public APIError() {
    this.timestamp = LocalDateTime.now().toString();
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}
