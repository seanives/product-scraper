package com.seanives.productscraper.parser;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.seanives.productscraper.ProductScraper;
import com.seanives.productscraper.aggregator.ProductResultsAggregator;
import com.seanives.productscraper.aggregator.aggregation.Aggregation;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.presenter.Presenter;
import com.seanives.productscraper.presenter.ProductPresenter;
import com.seanives.productscraper.reporter.ProductReporter;
import com.seanives.productscraper.reporter.Reporter;
import com.seanives.productscraper.reporter.report.BasicJsonProductReport;
import com.seanives.productscraper.reporter.report.JsonReportRenderer;
import com.seanives.productscraper.reporter.report.Report;

import static java.lang.System.exit;

public class ParserModule extends AbstractModule {

  public static final String PRODUCTS_PAGE_URL =
      "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

  @Override
  protected void configure() {
    try {
      bind(String.class).toInstance(PRODUCTS_PAGE_URL);
      bind(ProductParser.class)
          .toConstructor(ProductParser.class.getConstructor(Presenter.class, String.class));

    } catch (NoSuchMethodException e) {
      System.err.println("Parser module configuration problem, aborting...");
      e.printStackTrace();
      exit(0);
    }
  }
}
