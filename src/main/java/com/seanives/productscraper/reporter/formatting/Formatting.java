package com.seanives.productscraper.reporter.formatting;

public interface Formatting {
  String mapKey(final String key);

  Object mapValue(final Object value);
}
