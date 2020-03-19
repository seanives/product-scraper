package com.seanives.productscraper.parser;

import com.seanives.productscraper.Presenter;
import com.seanives.productscraper.model.ProductModel;
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

  public ProductParser(final Presenter presenter, final String productsPageUrl) {
    this.presenter = presenter;
    this.productsPageUrl = productsPageUrl;
  }

  public void getProducts(final Presenter presenter) throws IOException {
    Document productsPage = getDocument(productsPageUrl);
    List<ProductModel> productsList = parseProductsPage(productsPage);

    presenter.parsingCompletedSuccesfully(productsList);
  }

  List<ProductModel> parseProductsPage(final Element productsPage) throws IOException {
    Elements products = productsPage.select(".productLister .product");
    List<ProductModel> productList = new ArrayList<>();
    for (Element product : products) {
      productList.add(parseProduct(product));
    }
    return productList;
  }

  ProductModel parseProduct(final Element product) throws IOException {
    Element productNameAndPromos =
        Optional.ofNullable(product.select(".productInfo .productNameAndPromotions a").first())
            .get();
    String productTitle = getTitle(productNameAndPromos);

    String productDetailsPageUrl = productNameAndPromos.absUrl("href");
    Document productDetails = getDocument(productDetailsPageUrl);
    String description = getDescription(productDetails);
    Optional<Integer> kCals = getKCals(productDetails);

    Element pricingDetails = Optional.of(product.select(".pricePerUnit").first()).get();
    double pricePerUnit = getPricePerUnit(pricingDetails);

    return new ProductModel(productTitle, kCals, pricePerUnit, description);
  }

  String getTitle(final Element productNameAndPromos) {
    return productNameAndPromos.text();
  }

  String getDescription(final Element productDetails) {
    return parseChildElements(
            productDetails,
            ".mainProductInfo #information.section h3 + *",
            element ->
                element.getElementsByTag("p").stream()
                    .map(Element::text)
                    .filter(content -> !"".equals(content.trim()))
                    .findFirst()
                    .get(),
            productTextElement ->
                Optional.ofNullable(productTextElement.previousElementSibling())
                    .map(prev -> prev.text().equals("Description"))
                    .orElse(false))
        .stream()
        .findFirst()
        .get();
  }

  Optional<Integer> getKCals(final Element productDetails) {
    return parseChildElements(
            productDetails,
            ".mainProductInfo #information.section h3 + *",
            element ->
                element.getElementsByTag("td").stream()
                    .map(Element::text)
                    .filter(content -> content.endsWith("kcal"))
                    .map(
                        kcalText ->
                            Integer.parseUnsignedInt(kcalText.replaceAll("([0-9]+).*", "$1")))
                    .findFirst(),
            productTextElement ->
                Optional.ofNullable(productTextElement.previousElementSibling())
                    .map(prev -> prev.text().equals("Nutrition"))
                    .orElse(false))
        .stream()
        .findFirst()
        .orElse(Optional.empty());
  }

  double getPricePerUnit(final Element pricingDetails) {
    return pricingDetails.getAllElements().stream()
        .map(Element::text)
        .findFirst()
        .map(priceText -> priceText.replaceAll("£(0|(?:[1-9][0-9]*))([.][0-9]+)?.*", "$1$2"))
        .map(Double::parseDouble)
        .get();
  }

  <T> List<T> parseChildElements(
      final Element doc,
      final String selector,
      final Function<Element, T> elementMapper,
      final Predicate<Element> elementFilter) {
    Elements elements = doc.select(selector);
    return elements.stream().filter(elementFilter).map(elementMapper).collect(Collectors.toList());
  }

  public Document getDocument(final String pageUrl) throws IOException {
    return new Document(pageUrl);
  }
}
