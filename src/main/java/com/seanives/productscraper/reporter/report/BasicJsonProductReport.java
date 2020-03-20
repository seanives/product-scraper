package com.seanives.productscraper.reporter.report;

import com.seanives.productscraper.model.ProductResultsModel;

public class BasicJsonProductReport implements Report {

  private final ProductResultsModel productResultsModel;
  private final JsonReportRenderer jsonReportRenderer;

  public BasicJsonProductReport(
      final ProductResultsModel productResultsModel, final JsonReportRenderer jsonReportRenderer) {
    this.productResultsModel = productResultsModel;
    this.jsonReportRenderer = jsonReportRenderer;
  }

  @Override
  public void render() {
    jsonReportRenderer.getRendered(productResultsModel);
  }

  @Override
  public String toString() {
    return null;
  }
}
