package com.seanives.productscraper.reporter;

import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.report.Report;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductReporterTest {

  @Mock ProductResultsModel productResultsModel;
  @Mock Report report;

  public ProductReporterTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("should generate a valid report")
  void generateReport() {
    Reporter reporter = new ProductReporter(report);
    Report generatedReport = reporter.generateReport(productResultsModel);
    assertThat(generatedReport, is(notNullValue()));
  }
}
