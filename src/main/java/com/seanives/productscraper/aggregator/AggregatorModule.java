package com.seanives.productscraper.aggregator;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.seanives.productscraper.aggregator.aggregation.Aggregation;
import com.seanives.productscraper.model.ProductModel;

import static java.lang.System.exit;

public class AggregatorModule extends AbstractModule {

  @Override
  protected void configure() {
    try {
      bind(new TypeLiteral<Aggregation<ProductModel>>() {}).toInstance(new Aggregation<>());
      bind(ProductResultsAggregator.class)
          .toConstructor(ProductResultsAggregator.class.getConstructor(Aggregation.class));

    } catch (NoSuchMethodException e) {
      System.err.println("Aggregator module configuration problem, aborting...");
      e.printStackTrace();
      exit(0);
    }
  }
}
