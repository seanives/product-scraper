package com.seanives.productscraper.parser;

import com.google.inject.Inject;
import com.seanives.productscraper.presenter.Presenter;
import com.seanives.productscraper.errors.parser.UnableToGetConnectionException;
import com.seanives.productscraper.errors.parser.UnableToParseProductDetailsException;
import com.seanives.productscraper.errors.parser.UnableToParseProductPageException;
import com.seanives.productscraper.model.ProductModel;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductParser {
  final String productsPageUrl;
  final Presenter presenter;

  @Inject
  public ProductParser(final Presenter presenter, final String productsPageUrl) {
    this.presenter = presenter;
    this.productsPageUrl = productsPageUrl;
  }

  public void parseProducts() {
    try {
      Document productsPage = getDocument(productsPageUrl);
      List<ProductModel> productsList = parseProductsPage(productsPage);

      presenter.parsingCompletedSuccesfully(productsList);

    } catch (UnableToGetConnectionException e) {
      presenter.unableToGetConnectionFailure(
          String.format(
              "Unable to get the connection for the url '%s': %s", e.getUrl(), e.getMessage()));
    } catch (UnableToParseProductPageException e) {
      presenter.unableToParseProductPageFailure(
          String.format(
              "Unable to parse the details on page '%s': %s", e.getUrl(), e.getMessage()));
    } catch (UnableToParseProductDetailsException e) {
      presenter.unableToParseProductDetailsFailure(
          String.format(
              "Unable to parse the details for product '%s' on page '%s': %s",
              e.getProductTitle(), e.getUrl(), e.getMessage()));
    }
  }

  List<ProductModel> parseProductsPage(final Element productsPage)
      throws UnableToParseProductPageException, UnableToGetConnectionException {
    Elements products = productsPage.select(".productLister .product");
    List<ProductModel> productList = new ArrayList<>();
    for (Element product : products) {
      productList.add(parseProduct(product));
    }
    return productList;
  }

  ProductModel parseProduct(final Element product)
      throws UnableToParseProductPageException, UnableToGetConnectionException {
    Element productNameAndPromos =
        Optional.ofNullable(product.select(".productInfo .productNameAndPromotions a").first())
            .orElseThrow(
                () ->
                    new UnableToParseProductPageException(
                        "Cannot find product name and promotions on page", productsPageUrl));
    String productTitle = getTitle(productNameAndPromos);

    String productDetailsPageUrl = productNameAndPromos.absUrl("href");
    Document productDetails = getDocument(productDetailsPageUrl);
    String description = getDescription(productDetailsPageUrl, productTitle, productDetails);
    Optional<Integer> kCals = getKCals(productDetailsPageUrl, productTitle, productDetails);

    Element pricingDetails =
        Optional.ofNullable(product.select(".pricePerUnit").first())
            .orElseThrow(
                () ->
                    new UnableToParseProductPageException(
                        "Cannot find product pricing on page", productsPageUrl));
    double pricePerUnit = getPricePerUnit(productDetailsPageUrl, productTitle, pricingDetails);

    return new ProductModel(productTitle, kCals, pricePerUnit, description);
  }

  String getTitle(final Element productNameAndPromos) {
    return productNameAndPromos.text();
  }

  String getDescription(
      final String pageUrl, final String productTitle, final Element productDetails) {
    return parseChildElements(
            productDetails,
            ".mainProductInfo #information.section h3 + *",
            element ->
                element.getElementsByTag("p").stream()
                    .map(Element::text)
                    .filter(content -> !"".equals(content.trim()))
                    .findFirst()
                    .orElseThrow(
                        () ->
                            new UnableToParseProductDetailsException(
                                "Description has no content", pageUrl, productTitle)),
            productTextElement ->
                Optional.ofNullable(productTextElement.previousElementSibling())
                    .map(prev -> prev.text().equals("Description"))
                    .orElse(false))
        .stream()
        .findFirst()
        .orElseThrow(
            () ->
                new UnableToParseProductDetailsException(
                    "Description cannot be found", pageUrl, productTitle));
  }

  Optional<Integer> getKCals(
      final String pageUrl, final String productTitle, final Element productDetails) {
    return parseChildElements(
            productDetails,
            ".mainProductInfo #information.section h3 + *",
            element -> {
              try {
                return element.getElementsByTag("td").stream()
                    .map(Element::text)
                    .filter(content -> content.endsWith("kcal"))
                    .map(
                        kcalText ->
                            Integer.parseUnsignedInt(kcalText.replaceAll("([0-9]+).*", "$1")))
                    .findFirst();
              } catch (NumberFormatException e) {
                throw new UnableToParseProductDetailsException(
                    "Error parsing kcals per 100g", pageUrl, productTitle);
              }
            },
            productTextElement ->
                Optional.ofNullable(productTextElement.previousElementSibling())
                    .map(prev -> prev.text().equals("Nutrition"))
                    .orElse(false))
        .stream()
        .findFirst()
        .orElse(Optional.empty());
  }

  double getPricePerUnit(
      final String pageUrl, final String productTitle, final Element pricingDetails) {
    try {
      return pricingDetails.getAllElements().stream()
          .map(Element::text)
          .findFirst()
          .map(priceText -> priceText.replaceAll("£(0|(?:[1-9][0-9]*))([.][0-9]+)?.*", "$1$2"))
          .map(Double::parseDouble)
          .get();
    } catch (NumberFormatException e) {
      throw new UnableToParseProductDetailsException(
          "Error parsing price per unit", pageUrl, productTitle);
    }
  }

  <T> List<T> parseChildElements(
      final Element doc,
      final String selector,
      final Function<Element, T> elementMapper,
      final Predicate<Element> elementFilter) {
    Elements elements = doc.select(selector);
    return elements.stream().filter(elementFilter).map(elementMapper).collect(Collectors.toList());
  }

  Document getDocument(final String pageUrl) throws UnableToGetConnectionException {
    try {
      return getConnection(pageUrl).get();
    } catch (IOException e) {
      throw new UnableToGetConnectionException(e, pageUrl);
    }
  }

  Connection getConnection(final String pageUrl) throws IOException {
    return Jsoup.connect(pageUrl);
  }
}
