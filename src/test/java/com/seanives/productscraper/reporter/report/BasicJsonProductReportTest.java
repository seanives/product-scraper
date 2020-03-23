package com.seanives.productscraper.reporter.report;

import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.formatting.ProductReportFormatting;
import com.seanives.productscraper.reporter.formatting.ReportFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BasicJsonProductReportTest {

  @Mock ProductResultsModel productResultsModel;
  @Mock JsonReportRenderer<ProductResultsModel> reportRenderer;
  @Mock ReportFormatter<ProductReportFormatting> reportFormatter;

  BasicJsonProductReport report;

  public BasicJsonProductReportTest() {
    MockitoAnnotations.initMocks(this);
  }

  @BeforeEach
  void beforeEach() {
    report = new BasicJsonProductReport(reportRenderer, reportFormatter);
  }

  @Test
  @DisplayName("should render a valid report")
  void render() {
    doReturn(Collections.emptyMap()).when(reportRenderer).getRendered(any());
    report.render(productResultsModel);
    verify(reportRenderer, times(1)).getRendered(productResultsModel);
  }

  @Nested
  @DisplayName("toString")
  public class ConvertToStringTests {

    @Test
    @DisplayName("should convert the rendered report to a string")
    void convertToString() {
      doReturn(Collections.emptyMap()).when(reportFormatter).getFormatted(anyMap());
      doReturn(Collections.emptyMap()).when(reportRenderer).getRendered(any());
      doReturn("xxx").when(reportRenderer).getJsonString(any());
      report.render(productResultsModel);
      String result = report.toString();
      assertThat(result, is(equalTo("xxx")));
      verify(reportRenderer, times(1)).getRendered(productResultsModel);
      verify(reportRenderer, times(1)).getJsonString(Collections.emptyMap());
      verify(reportFormatter, times(1)).getFormatted(Collections.emptyMap());
    }

    @Test
    @DisplayName("should throw an exception if the report has not been rendered")
    void convertToStringThrows() {
      assertThat(
          assertThrows(IllegalStateException.class, () -> report.toString()).getMessage(),
          is(equalTo("report has not been rendered")));
    }
  }
}
