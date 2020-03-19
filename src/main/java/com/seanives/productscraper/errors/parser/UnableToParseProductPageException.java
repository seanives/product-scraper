package com.seanives.productscraper.errors.parser;

import java.util.NoSuchElementException;

public class UnableToParseProductPageException extends NoSuchElementException {

  public UnableToParseProductPageException(final String s) {
    super(s);
  }
}
