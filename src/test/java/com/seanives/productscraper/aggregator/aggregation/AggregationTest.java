package com.seanives.productscraper.aggregator.aggregation;

import com.seanives.productscraper.model.ProductModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;

public class AggregationTest {

  static final double TEST_VAT_RATE = 0.2d; // 20%

  @Mock ProductModel product1;
  @Mock ProductModel product2;
  @Mock ProductModel product3;

  @Spy Aggregation<ProductModel> aggregation = new Aggregation<>();

  public AggregationTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("should calculate the gross total correctly")
  void calculateGross() {
    doReturn(1.10d).when(product1).getUnitPrice();
    doReturn(2.20d).when(product2).getUnitPrice();
    doReturn(3.30d).when(product3).getUnitPrice();
    double gross = aggregation.calculateGross(Arrays.asList(product1, product2, product3));
    assertThat(gross, is(equalTo(6.6d)));
  }

  @Test
  @DisplayName("should calculate the vat total correctly")
  void calculateVat() {
    double vat = aggregation.calculateVat(12345.0d);
    assertThat(vat, is(equalTo(12345.0d * TEST_VAT_RATE)));
  }
}
