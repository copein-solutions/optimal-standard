package com.optimal.standard.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

  private String error;

  private String message;

  private Integer status;

  private List<String> details;

  public ErrorResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public ErrorResponse(String error, String message, Integer status) {
    this.error = error;
    this.message = message;
    this.status = status;
  }

  public ErrorResponse() {
  }

  public String getError() {
    return this.error;
  }

  public void setError(final String error) {
    this.error = error;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(final Integer status) {
    this.status = status;
  }

  public List<String> getDetails() {
    return this.details;
  }

  public void setDetails(final List<String> details) {
    this.details = details;
  }

}
