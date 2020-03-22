package com.seanives.productscraper.aggregator;

import com.seanives.productscraper.aggregator.aggregation.Aggregation;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductTotalModel;

import java.util.List;

public class ProductResultsAggregator {
  private final List<ProductModel> products;
  private final Aggregation<ProductModel> aggregation;

  public ProductResultsAggregator(
      final List<ProductModel> products, final Aggregation<ProductModel> aggregation) {
    this.products = products;
    this.aggregation = aggregation;
  }

  public ProductTotalModel getTotal() {
    double gross = aggregation.calculateGross(products);
    return new ProductTotalModel(gross, aggregation.calculateVat(gross));
  }
}
