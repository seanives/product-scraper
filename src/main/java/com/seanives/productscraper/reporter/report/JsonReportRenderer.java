package com.seanives.productscraper.reporter.report;

import org.json.JSONObject;
import java.util.Map;

public class JsonReportRenderer<T> {

  public String getJsonString(Map<String, Object> renderedReport) {
    return new JSONObject(renderedReport).toString();
  }

  public Map<String, Object> getRendered(T modelToRender) {
    return new JSONObject(modelToRender).toMap();
  }
}
