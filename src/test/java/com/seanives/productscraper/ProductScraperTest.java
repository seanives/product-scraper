package com.seanives.productscraper;

import com.seanives.productscraper.parser.ProductParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductScraperTest {

  @Mock ProductParser parser;

  public ProductScraperTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("invokes the parser to parse the products page")
  void invokesParser() {
    new ProductScraper(parser);
    verify(parser, times(1)).parseProducts();
  }
}
