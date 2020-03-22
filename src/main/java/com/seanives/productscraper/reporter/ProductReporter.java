package com.seanives.productscraper.reporter;

import com.google.inject.Inject;
import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.report.Report;

public class ProductReporter implements Reporter<ProductResultsModel> {
  private final Report report;

  @Inject
  public ProductReporter(final Report<ProductResultsModel> report) {
    this.report = report;
  }

  @Override
  public Report<ProductResultsModel> generateReport(final ProductResultsModel productResultsModel) {
    report.render(productResultsModel);
    return report;
  }
}
