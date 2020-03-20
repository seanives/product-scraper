package com.seanives.productscraper.reporter.report;

import com.seanives.productscraper.model.ProductResultsModel;

import java.util.Map;
import java.util.Optional;

public class BasicJsonProductReport implements Report {

  private final ProductResultsModel productResultsModel;
  private final JsonReportRenderer<ProductResultsModel> jsonReportRenderer;

  private Optional<Map<String,Object>> renderedReport = Optional.empty();

  public BasicJsonProductReport(
          final ProductResultsModel productResultsModel, final JsonReportRenderer<ProductResultsModel> jsonReportRenderer) {
    this.productResultsModel = productResultsModel;
    this.jsonReportRenderer = jsonReportRenderer;
  }

  @Override
  public void render() {
    renderedReport = Optional.of(jsonReportRenderer.getRendered(productResultsModel));
  }

  @Override
  public String toString() {
    return jsonReportRenderer.getJsonString(renderedReport.orElseThrow(() -> new IllegalStateException("report has not been rendered")));
  }
}
