package com.seanives.productscraper.reporter;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.seanives.productscraper.ProductScraper;
import com.seanives.productscraper.aggregator.ProductResultsAggregator;
import com.seanives.productscraper.aggregator.aggregation.Aggregation;
import com.seanives.productscraper.model.ProductModel;
import com.seanives.productscraper.model.ProductResultsModel;
import com.seanives.productscraper.parser.ProductParser;
import com.seanives.productscraper.presenter.Presenter;
import com.seanives.productscraper.presenter.ProductPresenter;
import com.seanives.productscraper.reporter.formatting.ProductReportFormatting;
import com.seanives.productscraper.reporter.formatting.ReportFormatter;
import com.seanives.productscraper.reporter.report.BasicJsonProductReport;
import com.seanives.productscraper.reporter.report.JsonReportRenderer;
import com.seanives.productscraper.reporter.report.Report;

import static java.lang.System.exit;

public class ReporterModule extends AbstractModule {

  @Override
  protected void configure() {
    try {
      bind(JsonReportRenderer.class).toInstance(new JsonReportRenderer<ProductResultsModel>());
      bind(new TypeLiteral<ReportFormatter<ProductReportFormatting>>() {}).toInstance(new ReportFormatter(new ProductReportFormatting()));
      bind(BasicJsonProductReport.class)
          .toConstructor(BasicJsonProductReport.class.getConstructor(JsonReportRenderer.class, ReportFormatter.class));
      bind(new TypeLiteral<Report<ProductResultsModel>>() {}).to(BasicJsonProductReport.class);
      bind(Reporter.class).to(ProductReporter.class);
      bind(ProductReporter.class).toConstructor(ProductReporter.class.getConstructor(Report.class));

    } catch (NoSuchMethodException e) {
      System.err.println("Reporter module configuration problem, aborting...");
      e.printStackTrace();
      exit(0);
    }
  }
}
