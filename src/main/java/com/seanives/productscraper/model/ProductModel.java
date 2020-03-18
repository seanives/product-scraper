package com.seanives.productscraper.model;

import java.util.Objects;
import java.util.Optional;

public class ProductModel {
  private final String title;
  private final Optional<Integer> kcalPer100g;
  private final double unitPrice;
  private final String description;

  public ProductModel(
      final String title,
      final Optional<Integer> kcalPer100g,
      final double unitPrice,
      final String description) {
    this.title = title;
    this.kcalPer100g = kcalPer100g;
    this.unitPrice = unitPrice;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public Optional<Integer> getKcalPer100g() {
    return kcalPer100g;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductModel that = (ProductModel) o;
    return Double.compare(that.unitPrice, unitPrice) == 0
        && title.equals(that.title)
        && kcalPer100g.equals(that.kcalPer100g)
        && description.equals(that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, kcalPer100g, unitPrice, description);
  }
}
