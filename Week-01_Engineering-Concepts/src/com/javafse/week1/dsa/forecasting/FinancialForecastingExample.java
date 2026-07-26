package com.javafse.week1.dsa.forecasting;

public class FinancialForecastingExample {
    public static void main(String[] args) {
        System.out.println("\n--- Exercise 7: Financial Forecasting ---");

        double currentValue = 10000.00;
        double annualGrowthRate = 0.10;
        int years = 5;

        FinancialForecaster forecaster = new FinancialForecaster();
        double futureValue = forecaster.calculateFutureValue(currentValue, annualGrowthRate, years);

        System.out.println("Current Value: " + currentValue);
        System.out.println("Annual Growth Rate: " + (annualGrowthRate * 100) + "%");
        System.out.println("Years: " + years);
        System.out.printf("Future Value: %.2f%n", futureValue);

        System.out.println("Recursion stops when years becomes 0. This is the base case.");
    }
}

