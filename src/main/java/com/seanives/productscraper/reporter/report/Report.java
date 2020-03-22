package com.seanives.productscraper.reporter.report;

public interface Report<R> {
    void render(final R resultsModel);
    String toString();
}
