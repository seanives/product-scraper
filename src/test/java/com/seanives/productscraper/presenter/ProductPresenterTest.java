package com.seanives.productscraper.presenter;

import com.seanives.productscraper.aggregator.ProductResultsAggregator;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.reporter.ProductReporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class ProductPresenterTest {

  @Mock ProductReporter reporter;
  @Mock ProductResultsAggregator resultsAggregator;
  @Mock ProductResultsModel productResults;
  @Mock List<ProductModel> products;
  ProductPresenter presenter;

  public ProductPresenterTest() {
    MockitoAnnotations.initMocks(this);
    presenter = new ProductPresenter(reporter, resultsAggregator);
  }

  @Test
  @DisplayName("should invoke the aggregator and reporter when parsing is complete")
  void parsingCompletedSuccesfully() {
    doAnswer(i -> productResults).when(resultsAggregator).getTotal(any());
    presenter.parsingCompletedSuccesfully(products);
    verify(resultsAggregator, times(1)).getTotal(products);
    verify(reporter, times(1)).generateReport(productResults);
  }
}
