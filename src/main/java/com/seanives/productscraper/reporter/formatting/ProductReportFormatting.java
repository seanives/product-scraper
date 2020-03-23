package com.seanives.productscraper.reporter.formatting;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class ProductReportFormatting implements Formatting {

  @Override
  public String mapKey(final String key) {
    switch (key) {
      case "kcalPer100g":
        return "kcal_per_100g";
      case "unitPrice":
        return "unit_price";
    }
    return key;
  }

  @Override
  public Object mapValue(final Object value) {
    if (value instanceof List) {
      return new ReportFormatter(this).getFormatted((List)value);
    }
    if (value instanceof Map) {
      return new ReportFormatter(this).getFormatted((Map)value);
    }
    if (value instanceof Double) {
      return roundToDP((Double) value, 2);
    }
    return value;
  }

  double roundToDP(final double value, final int decimalPlaces) {
    return new BigDecimal(value).setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
  }
}
