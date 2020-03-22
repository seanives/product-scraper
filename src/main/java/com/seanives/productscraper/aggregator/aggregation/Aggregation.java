package com.seanives.productscraper.aggregator.aggregation;

import java.util.List;

public class Aggregation<T extends Aggregatable> {
  double calculateGross(List<T> itemsToTotal) {
    return 0;
  }

  double calculateVat(double gross) {
    return 0;
  }
}
