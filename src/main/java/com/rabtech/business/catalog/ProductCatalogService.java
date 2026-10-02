package com.rabtech.business.catalog;

import com.rabtech.business.model.Product;

import java.util.List;

public class ProductCatalogService {

    public List<Product> filterByMaximumPrice(
            List<Product> products,
            double maximumPrice
    ) {
        return products.stream()
                .filter(product -> product.price() <= maximumPrice)
                .toList();
    }

    public List<Product> sortByPriceAscending(List<Product> products) {
        return products.stream()
                .sorted((product1, product2) ->
                        Double.compare(product1.price(), product2.price()))
                .toList();
    }
}