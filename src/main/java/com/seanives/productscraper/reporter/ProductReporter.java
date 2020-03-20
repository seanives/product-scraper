package com.seanives.productscraper.reporter;

import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.report.Report;

public class ProductReporter {
  private final ProductResultsModel productResultsModel;
  private final Report report;

  public ProductReporter(ProductResultsModel productResultsModel, Report report) {
    this.productResultsModel = productResultsModel;
    this.report = report;
  }

  public Report generateReport() {
    report.render();
    return report;
  }
}
