package com.seanives.productscraper.parser;

import com.seanives.productscraper.Presenter;
import com.seanives.productscraper.model.ProductModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.seanives.productscraper.parser.ProductParserFixtures.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductParserTest {
  static final String testProductUrl = "http://example.com/product-page.html";
  static final String testProductDetailsUrl = "http://example.com/product-details-page.html";

  static final ProductModel testProductModel =
      new ProductModel(
          TEST_PRODUCT_TITLE,
          Optional.of(TEST_PRODUCT_KCALS),
          TEST_PRODUCT_PRICE,
          TEST_PRODUCT_DESCRIPTION);

  @Mock Presenter mockPresenter;
  @Mock Document mockDocument;
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
    doReturn(mockDocument).when(productParser).getDocument(anyString());
    doReturn(Arrays.asList(testProductModel)).when(productParser).parseProductsPage(any());
    productParser.getProducts(mockPresenter);
    ArgumentCaptor<List<ProductModel>> productListCaptor = ArgumentCaptor.forClass(List.class);
    verify(mockPresenter, times(1)).parsingCompletedSuccesfully(productListCaptor.capture());
    assertThat(productListCaptor.getValue(), is(notNullValue()));
    assertThat(productListCaptor.getValue().size(), is(1));
    assertThat(productListCaptor.getValue().get(0), is(equalTo(testProductModel)));
  }

  @Test
  @DisplayName("should parse a page of products")
  void parseProductsPage() {
    doReturn(mockProducts).when(mockProductsPage).getAllElements();
    doReturn(Arrays.asList(mockProduct).iterator()).when(mockProducts).iterator();
    List<ProductModel> productList = productParser.parseProductsPage(mockProductsPage);
    assertThat(productList, is(notNullValue()));
    assertThat(productList.size(), is(equalTo(1)));
    verify(productParser, times(1)).parseProduct(mockProduct);
    verify(mockProductsPage, times(1)).select(".productLister .product");
  }

  @Test
  @DisplayName("should parse a product")
  void parseProduct() {
    Element productPage = mock(Element.class);
    Element productNameAndPromos = mock(Element.class);
    Elements products = mock(Elements.class);
    doReturn(productNameAndPromos).when(products).first();
    Element pricingDetails = mock(Element.class);
    Elements pricing = mock(Elements.class);
    doReturn(pricingDetails).when(pricing).first();
    doReturn(products, pricing).when(productPage).select(anyString());
    doReturn(mockDocument).when(productParser).getDocument(anyString());
    ProductModel productModel = productParser.parseProduct(mockProduct);
    assertThat(productModel, is(notNullValue()));
    verify(mockProduct, times(1)).select(".productInfo .productNameAndPromotions a");
    verify(mockProduct, times(1)).select(".pricePerUnit");
    verify(productParser, times(1)).getDocument(testProductDetailsUrl);
    verify(productParser, times(1)).getTitle(mockProduct);
    verify(productParser, times(1)).getPricePerUnit(mockProduct);
    verify(productParser, times(1)).getDescription(mockDocument);
    verify(productParser, times(1)).getKCals(mockDocument);

  }

  @Test
  @DisplayName("should return the product title")
  void getTitle() {
    Element productNameAndPromos = mock(Element.class);
    String title = productParser.getTitle(productNameAndPromos);
    assertThat(title, is(equalTo(TEST_PRODUCT_TITLE)));
    verify(productNameAndPromos, times(1)).text();
  }

  @Test
  @DisplayName("should return the price per unit")
  void getPricePerUnit() {
    Element pricingDetails = mock(Element.class);
    double price = productParser.getPricePerUnit(pricingDetails);
    assertThat(price, is(equalTo(TEST_PRODUCT_PRICE)));
    verify(pricingDetails, times(1)).getAllElements();
  }

  @Test
  @DisplayName("should return the description")
  void getDescription() {
    String description = productParser.getDescription(mockProduct);
    assertThat(description, is(equalTo(TEST_PRODUCT_DESCRIPTION)));
    verify(mockProduct, times(1)).select(".mainProductInfo #information.section h3 + *");
  }

  @Test
  @DisplayName("should return kcals per 100g")
  void getKCals() {
    Optional<Integer> kcals = productParser.getKCals(mockProduct);
    assertTrue(kcals.isPresent());
    assertThat(kcals.get(), is(equalTo(TEST_PRODUCT_KCALS)));
    verify(mockProduct, times(1)).select(".mainProductInfo #information.section h3 + *");
  }

  @Test
  @DisplayName("should return the page of products at the given url")
  void getDocument() {
    Document doc = productParser.getDocument(testProductUrl);
    assertThat(doc, is(notNullValue()));
  }
}
