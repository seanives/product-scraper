package com.seanives.productscraper.errors.parser;

import java.util.NoSuchElementException;

public class UnableToParseProductDetailsException extends NoSuchElementException {
  final String productTitle;
  final String url;

  public UnableToParseProductDetailsException(
      final String s, final String url, final String productTitle) {
    super(s);
    this.productTitle = productTitle;
    this.url = url;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public String getUrl() {
    return this.url;
  }
}
