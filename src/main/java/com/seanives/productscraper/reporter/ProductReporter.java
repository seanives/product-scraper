package com.seanives.productscraper.reporter;

import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.report.BasicJsonProductReport;
import com.seanives.productscraper.reporter.report.JsonReportRenderer;
import com.seanives.productscraper.reporter.report.Report;

public class ProductReporter {
  private final ProductResultsModel productResultsModel;
  private final Report report;

  public ProductReporter(ProductResultsModel productResultsModel) {
    this.productResultsModel = productResultsModel;
    this.report = new BasicJsonProductReport(productResultsModel, new JsonReportRenderer());
  }

  public Report generateReport() {
    report.render();
    return report;
  }
}
