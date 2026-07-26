package com.javafse.week1.dsa.search;

public class ECommerceSearchExample {
    public static void main(String[] args) {
        System.out.println("\n--- Exercise 2: E-commerce Platform Search Function ---");

        Product[] products = {
                new Product(101, "Laptop", "Electronics"),
                new Product(102, "Shoes", "Fashion"),
                new Product(103, "Smartphone", "Electronics"),
                new Product(104, "Notebook", "Stationery"),
                new Product(105, "Backpack", "Travel")
        };

        SearchService searchService = new SearchService();

        Product linearSearchResult = searchService.linearSearch(products, 103);
        Product binarySearchResult = searchService.binarySearch(products, 104);

        System.out.println("Linear Search Result: " + linearSearchResult);
        System.out.println("Binary Search Result: " + binarySearchResult);

        System.out.println("Linear Search Time Complexity: O(n)");
        System.out.println("Binary Search Time Complexity: O(log n), but the array must be sorted by productId.");
    }
}

