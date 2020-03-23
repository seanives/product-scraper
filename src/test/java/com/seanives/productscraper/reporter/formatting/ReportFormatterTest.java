package com.seanives.productscraper.reporter.formatting;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFormatterTest {

  final Map<String, Object> testUnformattedRenderingMap =
      new HashMap<String, Object>() {
        {
          put("key", "value");
        }
      };

  @Mock ProductReportFormatting formatting;

  final ReportFormatter<ProductReportFormatting> reportFormatter;

  public ReportFormatterTest() {
    MockitoAnnotations.initMocks(this);
    reportFormatter = new ReportFormatter<>(formatting);
  }

  @Test
  public void getFormattedMap() {
    Map<String, Object> formattedRendering =
        reportFormatter.getFormatted(testUnformattedRenderingMap);
    assertThat(formattedRendering, is(notNullValue()));
  }

  @Test
  public void getFormattedList() {
    List formattedRendering =
        reportFormatter.getFormatted(Arrays.asList("value"));
    assertThat(formattedRendering, is(notNullValue()));
  }
}
