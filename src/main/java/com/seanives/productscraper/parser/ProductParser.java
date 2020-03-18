package com.seanives.productscraper.parser;

import com.seanives.productscraper.Presenter;
import com.seanives.productscraper.model.ProductModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductParser {
  final String productsPageUrl;
  final Presenter presenter;

  public ProductParser(final Presenter presenter, final String productsPageUrl) {
    this.presenter = presenter;
    this.productsPageUrl = productsPageUrl;
  }

  public void getProducts(final Presenter presenter) {
    Document productsPage = getDocument(productsPageUrl);
    List<ProductModel> productsList = parseProductsPage(productsPage);

    presenter.parsingCompletedSuccesfully(productsList);
  }

  List<ProductModel> parseProductsPage(final Element productsPage) {
    List<ProductModel> productList = new ArrayList<>();
    for (Element product : productsPage.getAllElements()) {
      productList.add(parseProduct(product));
    }
    return productList;
  }

  ProductModel parseProduct(final Element product) {
    return new ProductModel(
        getTitle(product), getKCals(product), getPricePerUnit(product), getDescription(product));
  }

  String getTitle(final Element product) {
    return null;
  }

  String getDescription(final Element product) {
    return null;
  }

  Optional<Integer> getKCals(final Element product) {
    return null;
  }

  double getPricePerUnit(final Element product) {
    return 0.0d;
  }

  public Document getDocument(final String pageUrl) {
    return null;
  }
}
