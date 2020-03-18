package com.seanives.productscraper.parser;

import com.seanives.productscraper.Presenter;
import com.seanives.productscraper.model.ProductModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ProductParserTest {
  static final String testProductUrl = "http://example.com/product-page.html";

  @Mock Presenter mockPresenter;
  @Mock Element mockProductsPage;
  @Mock Elements mockProducts;
  @Mock Element mockProduct;

  @Spy ProductParser productParser = new ProductParser(mockPresenter, testProductUrl);

  public ProductParserTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("should get a list of parsed products")
  void getProducts() {
    productParser.getProducts(mockPresenter);
    verify(mockPresenter, times(1)).parsingCompletedSuccesfully(any());
  }

  @Test
  @DisplayName("should parse a page of products")
  void parseProductsPage() {
    List<ProductModel> productList = productParser.parseProductsPage(mockProductsPage);
    assertThat(productList, is(notNullValue()));
  }

  @Test
  @DisplayName("should parse a product")
  void parseProduct() {
    ProductModel productModel = productParser.parseProduct(mockProduct);
    assertThat(productModel, is(notNullValue()));
  }

  @Test
  @DisplayName("should return the product title")
  void getTitle() {
    String title = productParser.getTitle(mockProduct);
    assertThat(title, is(notNullValue()));
  }

  @Test
  @DisplayName("should return the price per unit")
  void getPricePerUnit() {
    double price = productParser.getPricePerUnit(mockProduct);
    assertThat(price, is(greaterThan(0.0d)));
  }

  @Test
  @DisplayName("should return the description")
  void getDescription() {
    String description = productParser.getDescription(mockProduct);
    assertThat(description, is(notNullValue()));
  }

  @Test
  @DisplayName("should return kcals per 100g")
  void getKCals() {
    Optional<Integer> kcals = productParser.getKCals(mockProduct);
    assertThat(kcals, is(notNullValue()));
  }

  @Test
  @DisplayName("should return the page of products at the given url")
  void getDocument() {
    Document doc = productParser.getDocument(testProductUrl);
    assertThat(doc, is(notNullValue()));
  }
}
