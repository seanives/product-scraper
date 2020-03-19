package com.seanives.productscraper.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.mockito.Mockito;

public class ProductParserFixtures {
    public static final String TEST_PRODUCT_TITLE = "test product title";
    public static final String TEST_PRODUCT_DESCRIPTION = "test product description";
    public static final double TEST_PRODUCT_PRICE = 123.45d;
    public static final int TEST_PRODUCT_KCALS = 123;

    public static final String DESCRIPTION_HEADING = "<h3>Description</h3>";
    public static final String PRODUCT_TEXT = "<div class=\"productText\"><p>%s</p></div>";

    public static final String NUTRITION_HEADING = "<h3>Nutrition</h3>";
    public static final String NUTRITION_TABLE = "<table class=\"nutritionTable\"><td>%s</td></div>";

    public static final String PRICE_PER_UNIT = "<p class=\"pricePerUnit\">%s</p>";

    public static Element getTestElement(String fixture) {
        return Mockito.spy(Jsoup.parse(fixture));
    }

}
