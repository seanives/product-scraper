package com.seanives.productscraper.reporter.formatting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ProductReportFormattingTest {

  ProductReportFormatting formatting = spy(new ProductReportFormatting());

  @Nested
  @DisplayName("mapKey")
  public class MapKeyTests {

    @Test
    @DisplayName("Should map an unknown key correctly")
    public void mapKeyUnknown() {
      String mappedKey = formatting.mapKey("key");
      assertThat(mappedKey, is(equalTo("key")));
    }

    @Test
    @DisplayName("Should map a known camel case key to snake case")
    public void mapKeyCamelCase() {
      String mappedKey = formatting.mapKey("kcalPer100g");
      assertThat(mappedKey, is(equalTo("kcal_per_100g")));
    }
  }

  @Nested
  @DisplayName("mapValue")
  public class MapValueTests {
    final Map<String, Object> nestedMap = new HashMap<String, Object>();
    final List nestedList = Arrays.asList("value");
    final Map<String, Object> testUnformattedRenderingMap =
        new HashMap<String, Object>() {
          {
            put("key", nestedMap);
          }
        };
    final Map<String, Object> testUnformattedRenderingList =
        new HashMap<String, Object>() {
          {
            put("key", nestedList);
          }
        };

    final ReportFormatter<ProductReportFormatting> reportFormatter =
        spy(new ReportFormatter<>(formatting));

    @Test
    public void mapValue() {
      Object mappedValue = formatting.mapValue(testUnformattedRenderingMap);
      assertThat(mappedValue, is(notNullValue()));
    }

    @Test
    @DisplayName("Should map a nested map")
    public void mapValueNestedMap() {
      reportFormatter.getFormatted(testUnformattedRenderingMap);
      verify(formatting, times(1)).mapValue(nestedMap);
      verify(reportFormatter, times(1)).getFormatted(testUnformattedRenderingMap);
    }

    @Test
    @DisplayName("Should map a nested list")
    public void mapValueNestedList() {
      reportFormatter.getFormatted(testUnformattedRenderingList);
      verify(formatting, times(1)).mapValue(nestedList);
      verify(reportFormatter, times(1)).getFormatted(testUnformattedRenderingList);
    }

    @Test
    @DisplayName("Should map a double to 2 decimal places")
    public void mapValueDouble() {
      formatting.mapValue(12.345678d);
      verify(formatting, times(1)).roundToDP(12.345678d, 2);
    }
  }

  @Test
  public void roundToDP() {
    double rounded = formatting.roundToDP(12.345678d, 2);
    assertThat(rounded, is(equalTo(12.35d)));
  }
}
