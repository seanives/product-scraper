package com.seanives.productscraper.presenter;

import com.seanives.productscraper.aggregator.ProductResultsAggregator;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.model.ProductTotalModel;
import com.seanives.productscraper.reporter.ProductReporter;
import com.seanives.productscraper.reporter.report.Report;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ProductPresenterTest {

  @Mock ProductReporter reporter;
  @Mock ProductResultsAggregator resultsAggregator;
  @Mock ProductTotalModel productTotals;
  @Mock ProductResultsModel productResults;
  @Mock List<ProductModel> products;
  @Mock Report report;
  ProductPresenter presenter;

  public ProductPresenterTest() {
    MockitoAnnotations.initMocks(this);
    presenter = new ProductPresenter(reporter, resultsAggregator);
  }

  @Test
  @DisplayName("should invoke the aggregator and reporter when parsing is complete")
  void parsingCompletedSuccesfully() {
    doAnswer(i -> productTotals).when(resultsAggregator).getTotal(any());
    doAnswer(i -> report).when(reporter).generateReport(any());
    presenter.parsingCompletedSuccesfully(products);
    verify(resultsAggregator, times(1)).getTotal(products);
    ArgumentCaptor<ProductResultsModel> argument =
        ArgumentCaptor.forClass(ProductResultsModel.class);
    verify(reporter, times(1)).generateReport(argument.capture());
    assertThat(argument.getValue().getResults(), is(equalTo(products)));
    assertThat(argument.getValue().getTotal(), is(equalTo(productTotals)));
  }
}
