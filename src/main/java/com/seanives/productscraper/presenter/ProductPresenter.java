package com.seanives.productscraper.presenter;

import com.google.inject.Inject;
import com.seanives.productscraper.aggregator.ProductResultsAggregator;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.Reporter;

import java.util.List;

import static java.lang.System.exit;

public class ProductPresenter implements Presenter {

  private final Reporter reporter;
  private final ProductResultsAggregator resultsAggregator;

  @Inject
  public ProductPresenter(
      final Reporter reporter, final ProductResultsAggregator resultsAggregator) {
    this.reporter = reporter;
    this.resultsAggregator = resultsAggregator;
  }

  @Override
  public void unableToGetConnectionFailure(final String errorMessage) {
    reportErrorAndStop(errorMessage);
  }

  @Override
  public void unableToParseProductPageFailure(final String errorMessage) {
    reportErrorAndStop(errorMessage);
  }

  @Override
  public void unableToParseProductDetailsFailure(final String errorMessage) {
    reportErrorAndStop(errorMessage);
  }

  @Override
  public void parsingCompletedSuccesfully(final List<ProductModel> productList) {
    ProductResultsModel productResults =
        new ProductResultsModel(productList, resultsAggregator.getTotal(productList));
    reportResults(reporter.generateReport(productResults).toString());
  }

  void reportErrorAndStop(final String errorMessage) {
    System.err.println(errorMessage);
    exit(0);
  }

  void reportResults(final String results) {
    System.out.println(results);
  }
}
