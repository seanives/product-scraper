package com.seanives.productscraper.errors.parser;

public class UnableToGetConnectionException extends Exception {
  final String url;

  public UnableToGetConnectionException(final Throwable cause, final String url) {
    super(cause.getMessage(), cause);
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }
}
