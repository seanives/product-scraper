package com.seanives.productscraper.reporter.report;

import com.google.inject.Inject;
import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.formatting.ReportFormatter;
import com.seanives.productscraper.reporter.formatting.ProductReportFormatting;

import java.util.Map;
import java.util.Optional;

public class BasicJsonProductReport implements Report<ProductResultsModel> {

  private final JsonReportRenderer<ProductResultsModel> jsonReportRenderer;
  private final ReportFormatter<ProductReportFormatting> reportFormatter;

  private Optional<Map<String, Object>> renderedReport = Optional.empty();

  @Inject
  public BasicJsonProductReport(
      final JsonReportRenderer<ProductResultsModel> jsonReportRenderer,
      final ReportFormatter<ProductReportFormatting> reportFormatter) {
    this.jsonReportRenderer = jsonReportRenderer;
    this.reportFormatter = reportFormatter;
  }

  @Override
  public void render(final ProductResultsModel productResultsModel) {
    renderedReport = Optional.of(jsonReportRenderer.getRendered(productResultsModel));
  }

  @Override
  public String toString() {
    return jsonReportRenderer.getJsonString(
        reportFormatter.getFormatted(
            renderedReport.orElseThrow(
                () -> new IllegalStateException("report has not been rendered"))));
  }
}
