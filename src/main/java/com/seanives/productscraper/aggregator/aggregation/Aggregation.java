package com.seanives.productscraper.aggregator.aggregation;

import java.util.List;

public class Aggregation<T extends Aggregatable> {

  static final double VAT_RATE = 0.2d; // 20%

  public double calculateGross(List<T> itemsToTotal) {
    return itemsToTotal.stream().map(Aggregatable::getUnitPrice).reduce(0.0d, Double::sum);
  }

  public double calculateVat(double gross) {
    return gross * VAT_RATE;
  }
}
