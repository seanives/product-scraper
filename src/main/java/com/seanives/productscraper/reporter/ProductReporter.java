package com.seanives.productscraper.reporter;

import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.report.BasicJsonProductReport;

public class ProductReporter {
  private final ProductResultsModel productResultsModel;

  public ProductReporter(ProductResultsModel productResultsModel) {
    this.productResultsModel = productResultsModel;
  }

  public BasicJsonProductReport generateReport() {
    return null;
  }
}
