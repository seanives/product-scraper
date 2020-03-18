package com.seanives.productscraper.reporter.report;

import com.seanives.productscraper.model.ProductResultsModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class BasicJsonProductReportTest {

  @Mock ProductResultsModel productResultsModel;

  @Mock JsonReportRenderer reportRenderer;

  public BasicJsonProductReportTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("should render a valid report")
  void render() {
    BasicJsonProductReport report = new BasicJsonProductReport(productResultsModel, reportRenderer);
    report.render();
    verify(reportRenderer, atLeastOnce()).getJsonString();
    assertThat(report, is(notNullValue()));
  }
}
