package com.seanives.productscraper;

import com.seanives.productscraper.model.ProductModel;

import java.util.List;

public interface Presenter {
   void unableToGetConnectionFailure(String errorMessage);
   void unableToParseProductPageFailure(String errorMessage);
   void unableToParseProductDetailsFailure(String errorMessage);

   void parsingCompletedSuccesfully(List<ProductModel> productList);

}
