package com.seanives.productscraper.aggregator;

import com.google.inject.Inject;
import com.seanives.productscraper.aggregator.aggregation.Aggregation;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductTotalModel;

import java.util.List;

public class ProductResultsAggregator {
  private final Aggregation<ProductModel> aggregation;

  @Inject
  public ProductResultsAggregator(final Aggregation<ProductModel> aggregation) {
    this.aggregation = aggregation;
  }

  public ProductTotalModel getTotal(final List<ProductModel> products) {
    double gross = aggregation.calculateGross(products);
    return new ProductTotalModel(gross, aggregation.calculateVat(gross));
  }
}
