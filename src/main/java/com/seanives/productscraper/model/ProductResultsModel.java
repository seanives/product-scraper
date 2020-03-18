package com.seanives.productscraper.model;

import java.util.List;

public class ProductResultsModel {
  private final List<ProductModel> results;
  private final ProductTotalModel total;

  public ProductResultsModel(final List results, final ProductTotalModel total) {
    this.results = results;
    this.total = total;
  }

  public ProductTotalModel getTotal() {
    return total;
  }

  public List<ProductModel> getResults() {
    return results;
  }
}
