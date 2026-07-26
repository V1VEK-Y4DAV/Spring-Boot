package com.javafse.week1;

import com.javafse.week1.designpatterns.factory.FactoryMethodPatternExample;
import com.javafse.week1.designpatterns.singleton.SingletonPatternExample;
import com.javafse.week1.dsa.forecasting.FinancialForecastingExample;
import com.javafse.week1.dsa.search.ECommerceSearchExample;

public class WeekOneDemo {
    public static void main(String[] args) {
        System.out.println("========== WEEK 1 - ENGINEERING CONCEPTS ==========");

        SingletonPatternExample.main(args);
        FactoryMethodPatternExample.main(args);
        ECommerceSearchExample.main(args);
        FinancialForecastingExample.main(args);

        System.out.println("\nAll Week 1 mandatory exercises executed successfully.");
    }
}

