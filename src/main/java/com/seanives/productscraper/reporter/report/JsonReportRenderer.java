package com.seanives.productscraper.reporter.report;

import java.util.Collections;
import java.util.Map;

public class JsonReportRenderer<T> {

  public String getJsonString(Map<String, Object> renderedReport) {
    return "";
  }

  public Map<String, Object> getRendered(T modelToRender) {
    return Collections.emptyMap();
  }
}
