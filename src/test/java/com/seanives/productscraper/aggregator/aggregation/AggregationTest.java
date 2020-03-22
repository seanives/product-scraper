package com.seanives.productscraper.aggregator.aggregation;

import com.seanives.productscraper.model.ProductModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AggregationTest {

  static final double TEST_VAT_RATE = 0.2d; // 20%

  @Mock ProductModel product1;
  @Mock ProductModel product2;
  @Mock ProductModel product3;

  @Spy Aggregation<ProductModel> aggregation = new Aggregation<>();

  @Test
  @DisplayName("should calculate the gross total correctly")
  void calculateGross() {
    double gross = aggregation.calculateGross(Arrays.asList(product1, product2, product3));
    assertThat(gross, is(greaterThan(0.0d)));
  }

  @Test
  @DisplayName("should calculate the vat total correctly")
  void calculateVat() {
    double vat = aggregation.calculateVat(12345.0d);
    assertThat(vat, is(equalTo(12345.0d * TEST_VAT_RATE)));
  }
}
