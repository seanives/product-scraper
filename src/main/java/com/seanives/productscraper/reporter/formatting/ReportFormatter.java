package com.seanives.productscraper.reporter.formatting;

import java.util.List;
import java.util.Map;

public class ReportFormatter<T extends Formatting> {

  private final T formatting;

  public ReportFormatter(final T formatting) {
    this.formatting = formatting;
  }

  public Map<String, Object> getFormatted(final Map<String, Object> unformatted) {
    return unformatted;
  }

  public List<Object> getFormatted(final List<Object> unformatted) {
    return unformatted;
  }
}
