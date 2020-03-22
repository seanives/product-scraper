package com.seanives.productscraper.presenter;

import com.google.inject.AbstractModule;
import com.seanives.productscraper.aggregator.ProductResultsAggregator;
import com.seanives.productscraper.reporter.Reporter;

import static java.lang.System.exit;

public class PresenterModule extends AbstractModule {

  @Override
  protected void configure() {
    try {
      bind(Presenter.class).to(ProductPresenter.class);
      bind(ProductPresenter.class)
          .toConstructor(
              ProductPresenter.class.getConstructor(
                  Reporter.class, ProductResultsAggregator.class));

    } catch (NoSuchMethodException e) {
      System.err.println("Presenter module configuration problem, aborting...");
      e.printStackTrace();
      exit(0);
    }
  }
}
