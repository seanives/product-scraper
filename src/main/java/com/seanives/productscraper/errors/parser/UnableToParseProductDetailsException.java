package com.seanives.productscraper.errors.parser;

import java.util.NoSuchElementException;

public class UnableToParseProductDetailsException extends NoSuchElementException {

  public UnableToParseProductDetailsException(final String s) {
    super(s);
  }
}
