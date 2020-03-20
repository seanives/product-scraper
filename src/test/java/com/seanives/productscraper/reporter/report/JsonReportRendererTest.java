package com.seanives.productscraper.reporter.report;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class JsonReportRendererTest {

  @Mock Object model;
  @Mock Map<String,Object> renderedReport;

  public JsonReportRendererTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("should return a rendered report when given a model")
  void getRendered() {
    JsonReportRenderer renderer = new JsonReportRenderer();
    Map<String,Object> renderedReport = renderer.getRendered(model);
    assertThat(renderedReport, is(notNullValue()));
  }

  @Test
  @DisplayName("should transform a given rendered report into a json string")
  void getJsonString() {
    JsonReportRenderer renderer = new JsonReportRenderer();
    String json = renderer.getJsonString(renderedReport);
    assertThat(json, is(notNullValue()));
  }
}
