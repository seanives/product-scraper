package com.seanives.productscraper.aggregator;

import com.seanives.productscraper.aggregator.aggregation.Aggregation;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductTotalModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductResultsAggregatorTest {

  @Mock ProductModel product;
  @Mock Aggregation<ProductModel> productAggregation;

  final List<ProductModel> productList;
  final ProductResultsAggregator aggregator;

  public ProductResultsAggregatorTest() {
    MockitoAnnotations.initMocks(this);
    productList = Arrays.asList(product);
    aggregator = new ProductResultsAggregator(productList, productAggregation);
  }

  @Test
  @DisplayName("should generate a valid product results total")
  void generateTotal() {
    doReturn(20.20d).when(productAggregation).calculateGross(any());
    doReturn(4.04d).when(productAggregation).calculateVat(anyDouble());
    ProductTotalModel total = aggregator.getTotal();
    assertThat(total, is(notNullValue()));
    assertThat(total.getGross(), is(equalTo(20.20d)));
    assertThat(total.getVat(), is(equalTo(4.04d)));
    verify(productAggregation, times(1)).calculateGross(productList);
    verify(productAggregation, times(1)).calculateVat(20.20d);
  }
}
