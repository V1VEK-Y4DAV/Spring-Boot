package com.javafse.week1.dsa.search;

public class SearchService {
    public Product linearSearch(Product[] products, int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    public Product binarySearch(Product[] sortedProducts, int productId) {
        int left = 0;
        int right = sortedProducts.length - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            int middleProductId = sortedProducts[middle].getProductId();

            if (middleProductId == productId) {
                return sortedProducts[middle];
            }

            if (middleProductId < productId) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return null;
    }
}

