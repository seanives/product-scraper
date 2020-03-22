package com.seanives.productscraper.presenter;

import com.google.inject.Inject;
import com.seanives.productscraper.aggregator.ProductResultsAggregator;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.reporter.Reporter;

import java.util.List;

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
  public void unableToGetConnectionFailure(String errorMessage) {}

  @Override
  public void unableToParseProductPageFailure(String errorMessage) {}

  @Override
  public void unableToParseProductDetailsFailure(final String errorMessage) {}

  @Override
  public void parsingCompletedSuccesfully(final List<ProductModel> productList) {}
}
