package com.seanives.productscraper.parser;

import com.seanives.productscraper.Presenter;
import com.seanives.productscraper.errors.parser.UnableToParseProductDetailsException;
import com.seanives.productscraper.errors.parser.UnableToParseProductPageException;
import com.seanives.productscraper.model.ProductModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.seanives.productscraper.parser.ProductParserFixtures.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
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

  @Nested
  @DisplayName("getProducts")
  public class GetProductsTests {

    @Test
    @DisplayName("should get a list of parsed products")
    void getProducts() throws IOException {
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
    @DisplayName("should invoke the presenter if unable to parse the products page")
    void getProductsCallsPresenterIfUnableToParseProducts() throws IOException {
      doReturn(mockDocument).when(productParser).getDocument(anyString());
      doThrow(new UnableToParseProductPageException("x", "y")).when(productParser).parseProductsPage(any());
      productParser.getProducts(mockPresenter);
      verify(mockPresenter, times(1)).unableToParseProductPageFailure("Unable to parse the details on page 'y': x");
    }

    @Test
    @DisplayName("should invoke the presenter if unable to parse the products page")
    void getProductsCallsPresenterIfUnableToParseProductDetails() throws IOException {
      doReturn(mockDocument).when(productParser).getDocument(anyString());
      doThrow(new UnableToParseProductDetailsException("x", "y", "z")).when(productParser).parseProductsPage(any());
      productParser.getProducts(mockPresenter);
      verify(mockPresenter, times(1)).unableToParseProductDetailsFailure("Unable to parse the details for product 'z' on page 'y': x");
    }
  }

  @Test
  @DisplayName("should parse a page of products")
  void parseProductsPage() throws IOException {
    doReturn(mockProducts).when(mockProductsPage).select(anyString());
    doReturn(Arrays.asList(mockProduct).iterator()).when(mockProducts).iterator();
    doReturn(testProductModel).when(productParser).parseProduct(any());
    List<ProductModel> productList = productParser.parseProductsPage(mockProductsPage);
    assertThat(productList, is(notNullValue()));
    assertThat(productList.size(), is(equalTo(1)));
    assertThat(productList.get(0), is(equalTo(testProductModel)));
    verify(productParser, times(1)).parseProduct(mockProduct);
    verify(mockProductsPage, times(1)).select(".productLister .product");
  }

  @Nested
  @DisplayName("parseProduct")
  public class ParseProductTests {

    Element productNameAndPromos = mock(Element.class);

    void setUp(Element pricingDetails) throws IOException {
      Elements pricing = mock(Elements.class);
      Elements products = mock(Elements.class);
      doReturn(productNameAndPromos).when(products).first();

      doReturn(pricingDetails).when(pricing).first();
      doReturn(products, pricing).when(mockProduct).select(anyString());
      doReturn(testProductDetailsUrl).when(productNameAndPromos).absUrl(anyString());
      doReturn(mockDocument).when(productParser).getDocument(anyString());
      doReturn(TEST_PRODUCT_TITLE).when(productParser).getTitle(any());
      doReturn(TEST_PRODUCT_PRICE).when(productParser).getPricePerUnit(anyString(), anyString(), any());
      doReturn(TEST_PRODUCT_DESCRIPTION).when(productParser).getDescription(anyString(), anyString(), any());
      doReturn(Optional.of(TEST_PRODUCT_KCALS)).when(productParser).getKCals(anyString(), anyString(), any());
    }

    @Test
    @DisplayName("should parse a product")
    void parseProduct() throws IOException {
      Element pricingDetails = mock(Element.class);
      setUp(pricingDetails);

      ProductModel productModel = productParser.parseProduct(mockProduct);
      assertThat(productModel, is(notNullValue()));
      assertThat(productModel, is(equalTo(testProductModel)));
      verify(mockProduct, times(1)).select(".productInfo .productNameAndPromotions a");
      verify(mockProduct, times(1)).select(".pricePerUnit");
      verify(productNameAndPromos, times(1)).absUrl("href");
      verify(productParser, times(1)).getDocument(testProductDetailsUrl);

      verify(productParser, times(1)).getTitle(any());
      verify(productParser, times(1)).getPricePerUnit(anyString(), anyString(), any());
      verify(productParser, times(1)).getDescription(anyString(), anyString(), any());
      verify(productParser, times(1)).getKCals(anyString(), anyString(), any());
    }

    @Test
    @DisplayName("should throw an exception if product name and promotions is missing on page")
    void parseProductThrowsIfProductNameAndPromosMissing() throws IOException {
      Element pricingDetails = mock(Element.class);
      setUp(pricingDetails);

      Elements products = mock(Elements.class);
      doReturn(null).when(products).first();
      doReturn(products).when(mockProduct).select(anyString());
      assertThat(
          assertThrows(
                  UnableToParseProductPageException.class,
                  () -> productParser.parseProduct(mockProduct))
              .getMessage(),
          is(equalTo("Cannot find product name and promotions on page")));
    }

    @Test
    @DisplayName("should throw an exception if pricing is missing on page")
    void parseProductThrowsIfProductPricingMissing() throws IOException {
      setUp(null);

      assertThat(
          assertThrows(
                  UnableToParseProductPageException.class,
                  () -> productParser.parseProduct(mockProduct))
              .getMessage(),
          is(equalTo("Cannot find product pricing on page")));
    }
  }

  @Test
  @DisplayName("should return the product title")
  void getTitle() {
    Element productNameAndPromos = mock(Element.class);
    doReturn(TEST_PRODUCT_TITLE).when(productNameAndPromos).text();
    String title = productParser.getTitle(productNameAndPromos);
    assertThat(title, is(equalTo(TEST_PRODUCT_TITLE)));
    verify(productNameAndPromos, times(1)).text();
  }

  @Nested
  @DisplayName("getPricePerUnit")
  public class GetPricePerUnitTests {

    @Test
    @DisplayName("should return the price per unit), if present and valid")
    void getPricePerUnit() {
      Element pricingDetails = getTestElement(String.format(PRICE_PER_UNIT, TEST_PRODUCT_PRICE));
      double price = productParser.getPricePerUnit(testProductUrl, TEST_PRODUCT_TITLE, pricingDetails);
      assertThat(price, is(equalTo(TEST_PRODUCT_PRICE)));
      verify(pricingDetails, times(1)).getAllElements();
    }

    @Test
    @DisplayName("should throw an exception if price per unit is missing")
    void getPricePerUnitThrowsIfMissing() {
      Element pricingDetails = getTestElement(String.format(PRICE_PER_UNIT, ""));
      assertThat(
          assertThrows(
                  UnableToParseProductDetailsException.class,
                  () -> productParser.getPricePerUnit(testProductUrl, TEST_PRODUCT_TITLE, pricingDetails))
              .getMessage(),
          is(equalTo("Error parsing price per unit")));
    }

    @Test
    @DisplayName("should throw an exception if price per unit is invalid")
    void getPricePerUnitThrowsIfInvalid() {
      Element pricingDetails = getTestElement(String.format(PRICE_PER_UNIT, "£xxx"));
      assertThat(
          assertThrows(
                  UnableToParseProductDetailsException.class,
                  () -> productParser.getPricePerUnit(testProductUrl, TEST_PRODUCT_TITLE, pricingDetails))
              .getMessage(),
          is(equalTo("Error parsing price per unit")));
    }
  }

  @Nested
  @DisplayName("information section")
  public class InfoSectionTests {

    Element targetElement;
    Element infoSectionHeading;

    private void setUp(
        final String targetElementText, final String heading, final String targetElementFixture) {
      Elements selected = mock(Elements.class);
      doReturn(selected).when(mockProduct).select(anyString());
      infoSectionHeading = getTestElement(heading);
      targetElement = getTestElement(String.format(targetElementFixture, targetElementText));
      doReturn(infoSectionHeading).when(targetElement).previousElementSibling();
      List<Element> els = Arrays.asList(infoSectionHeading, targetElement);
      doReturn(els.stream()).when(selected).stream();
    }

    @Nested
    @DisplayName("getDescription")
    public class GetDescriptionTests {
      @Test
      @DisplayName("should return the description, if present and valid")
      void getDescription() {
        setUp(TEST_PRODUCT_DESCRIPTION, DESCRIPTION_HEADING, PRODUCT_TEXT);
        String description = productParser.getDescription(testProductUrl, TEST_PRODUCT_TITLE, mockProduct);
        assertThat(description, is(equalTo(TEST_PRODUCT_DESCRIPTION)));
        verify(mockProduct, times(1)).select(".mainProductInfo #information.section h3 + *");
        verify(targetElement, times(1)).previousElementSibling();
        verify(infoSectionHeading, times(1)).text();
        verify(targetElement, times(1)).getElementsByTag("p");
      }

      @Test
      @DisplayName("should throw an exception if the description is blank")
      void getDescriptionThrowsWhenNoContent() {
        setUp("", DESCRIPTION_HEADING, "<div/>");
        assertThat(
            assertThrows(
                    UnableToParseProductDetailsException.class,
                    () -> productParser.getDescription(testProductUrl, TEST_PRODUCT_TITLE, mockProduct))
                .getMessage(),
            is(equalTo("Description has no content")));
      }

      @Test
      @DisplayName("should throw an exception if the description is missing")
      void getDescriptionThrowsWhenNotFound() {
        setUp("", "<h3>Not a Description<h3/>", "<div/>");
        assertThat(
            assertThrows(
                    UnableToParseProductDetailsException.class,
                    () -> productParser.getDescription(testProductUrl, TEST_PRODUCT_TITLE, mockProduct))
                .getMessage(),
            is(equalTo("Description cannot be found")));
      }
    }

    @Nested
    @DisplayName("getKCals")
    public class GetKCalsTests {

      @Test
      @DisplayName("should return kcals per 100g, if present and valid")
      void getKCals() {
        String testKcalsText = String.format("%dkcal", TEST_PRODUCT_KCALS);
        setUp(testKcalsText, NUTRITION_HEADING, NUTRITION_TABLE);
        Optional<Integer> kcals = productParser.getKCals(testProductUrl, TEST_PRODUCT_TITLE, mockProduct);
        assertTrue(kcals.isPresent());
        assertThat(kcals.get(), is(equalTo(TEST_PRODUCT_KCALS)));
        verify(mockProduct, times(1)).select(".mainProductInfo #information.section h3 + *");
        verify(targetElement, times(1)).previousElementSibling();
        verify(infoSectionHeading, times(1)).text();
        verify(targetElement, times(1)).getElementsByTag("td");
      }

      @Test
      @DisplayName("should throw an exception if kcals per 100g is present, but is invalid")
      void getKCalsThrowsWhenInvalid() {
        setUp("xxxkcal", NUTRITION_HEADING, NUTRITION_TABLE);
        assertThat(
            assertThrows(
                    UnableToParseProductDetailsException.class,
                    () -> productParser.getKCals(testProductUrl, TEST_PRODUCT_TITLE, mockProduct))
                .getMessage(),
            is(equalTo("Error parsing kcals per 100g")));
      }

      @Test
      @DisplayName("should return an empty optional if the kcals per 100g is missing")
      void getKCalsReturnsEmptyWhenNotFound() {
        setUp("", "<h3>Not Nutrition</h3>", "<div/>");
        Optional<Integer> kcals = productParser.getKCals(testProductUrl, TEST_PRODUCT_TITLE, mockProduct);
        assertFalse(kcals.isPresent());
      }
    }
  }

  @Test
  @DisplayName("should return the page of products at the given url")
  void getDocument() throws IOException {
    Document doc = productParser.getDocument(testProductUrl);
    assertThat(doc, is(notNullValue()));
  }
}
