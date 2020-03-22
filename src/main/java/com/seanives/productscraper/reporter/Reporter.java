package com.seanives.productscraper.reporter;

import com.seanives.productscraper.reporter.report.Report;

public interface Reporter<R> {
    Report<R> generateReport(R result);
}
