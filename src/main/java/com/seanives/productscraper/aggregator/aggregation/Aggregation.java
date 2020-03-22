package com.seanives.productscraper.aggregator.aggregation;

import java.util.List;

public class Aggregation<T extends Aggregatable> {

  static final double VAT_RATE = 0.2d; // 20%

  double calculateGross(List<T> itemsToTotal) {
    return itemsToTotal.stream().map(Aggregatable::getUnitPrice).reduce(0.0d, Double::sum);
  }

  double calculateVat(double gross) {
    return gross * VAT_RATE;
  }
}
