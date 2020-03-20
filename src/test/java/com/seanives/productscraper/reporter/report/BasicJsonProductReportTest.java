package com.seanives.productscraper.reporter.report;

import com.seanives.productscraper.model.ProductResultsModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BasicJsonProductReportTest {

  @Mock ProductResultsModel productResultsModel;
  @Mock JsonReportRenderer<ProductResultsModel> reportRenderer;

  BasicJsonProductReport report;

  public BasicJsonProductReportTest() {
    MockitoAnnotations.initMocks(this);
    report = new BasicJsonProductReport(productResultsModel, reportRenderer);
  }

  @Test
  @DisplayName("should render a valid report")
  void render() {
    report.render();
    verify(reportRenderer, times(1)).getRendered(productResultsModel);
  }

  @Test
  @DisplayName("should convert the rendered report to a string")
  void convertToString() {
    String result = report.toString();
    assertThat(result, is(notNullValue()));
  }
}
