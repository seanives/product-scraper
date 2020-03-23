package com.seanives.productscraper.reporter.formatting;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductReportFormattingTest {

  ProductReportFormatting formatting = new ProductReportFormatting();

  @Test
  public void mapKey() {
    String mappedKey = formatting.mapKey("key");
    assertThat(mappedKey, is(notNullValue()));
  }

  @Test
  public void mapValue() {
    Object mappedValue = formatting.mapValue("value");
    assertThat(mappedValue, is(notNullValue()));
  }

  @Test
  public void roundToDP() {
    double rounded = formatting.roundToDP(12.345678d, 2);
    assertThat(rounded, is(equalTo(12.35d)));
  }
}
