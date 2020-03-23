package com.seanives.productscraper.reporter.formatting;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportFormatter<T extends Formatting> {

  private final T formatting;

  public ReportFormatter(final T formatting) {
    this.formatting = formatting;
  }

  public Map<String, Object> getFormatted(final Map<String, Object> unformatted) {
    return unformatted.entrySet().stream()
        .collect(
            Collectors.toMap(
                e -> formatting.mapKey(e.getKey()), e -> formatting.mapValue(e.getValue())));
  }

  public List<Object> getFormatted(final List<Object> unformatted) {
    return unformatted.stream().map(formatting::mapValue).collect(Collectors.toList());
  }
}
