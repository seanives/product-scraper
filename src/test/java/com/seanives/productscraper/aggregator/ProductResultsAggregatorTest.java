package com.seanives.productscraper.aggregator;

import com.seanives.productscraper.aggregator.aggregation.Aggregation;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductTotalModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductResultsAggregatorTest {

  @Mock ProductModel product;
  @Mock Aggregation<ProductModel> productAggregation;

  @Spy
  ProductResultsAggregator aggregator =
      new ProductResultsAggregator(Arrays.asList(product), productAggregation);

  @Test
  @DisplayName("should generate a valid product results total")
  void generateTotal() {
    ProductTotalModel total = aggregator.getTotal();
    assertThat(total, is(notNullValue()));
  }
}
