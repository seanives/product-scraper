package com.seanives.productscraper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.seanives.productscraper.aggregator.AggregatorModule;
import com.seanives.productscraper.parser.ParserModule;
import com.seanives.productscraper.parser.ProductParser;
import com.seanives.productscraper.presenter.PresenterModule;
import com.seanives.productscraper.reporter.ReporterModule;

public class ProductScraper {

  public static void main(String[] args) {
    Injector injector =
        Guice.createInjector(
            new ParserModule(),
            new AggregatorModule(),
            new ReporterModule(),
            new PresenterModule());

    new ProductScraper(injector.getInstance(ProductParser.class));
  }

  public ProductScraper(final ProductParser parser) {
    parser.parseProducts();
  }
}
