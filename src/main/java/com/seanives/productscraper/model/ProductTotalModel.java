package com.seanives.productscraper.model;

public class ProductTotalModel {
  private final double gross;
  private final double vat;

  public ProductTotalModel(final double gross, final double vat) {
    this.gross = gross;
    this.vat = vat;
  }

  public double getGross() {
    return gross;
  }

  public double getVat() {
    return vat;
  }
}
