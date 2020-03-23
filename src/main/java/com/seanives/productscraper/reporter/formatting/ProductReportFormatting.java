package com.seanives.productscraper.reporter.formatting;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductReportFormatting implements Formatting {

  @Override
  public String mapKey(final String key) {
    return null;
  }

  @Override
  public Object mapValue(final Object value) {
    return null;
  }

  double roundToDP(final double value, final int decimalPlaces) {
    return new BigDecimal(value).setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
  }
}
