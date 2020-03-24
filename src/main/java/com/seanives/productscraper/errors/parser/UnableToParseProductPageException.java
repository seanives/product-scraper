package com.seanives.productscraper.errors.parser;

import java.util.NoSuchElementException;

public class UnableToParseProductPageException extends NoSuchElementException {
  private final String url;

  public UnableToParseProductPageException(final String s, final String url) {
    super(s);
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
