# Week 01 - Engineering Concepts

This folder contains the mandatory hands-on solutions for Week 01 of the Java FSE Deep Skilling Program.

## Exercises Covered

### Design Patterns and Principles

1. Exercise 1: Implementing the Singleton Pattern
2. Exercise 2: Implementing the Factory Method Pattern

### Data Structures and Algorithms

1. Exercise 2: E-commerce Platform Search Function
2. Exercise 7: Financial Forecasting

## Project Structure

```text
Week-01_Engineering-Concepts/
  src/
    com/javafse/week1/
      WeekOneDemo.java
      designpatterns/
        singleton/
        factory/
      dsa/
        search/
        forecasting/
```

## How to Run

From this folder, compile the project:

```bash
javac -d out $(find src -name "*.java")
```

Run the complete Week 1 demo:

```bash
java -cp out com.javafse.week1.WeekOneDemo
```

You can also run each exercise separately:

```bash
java -cp out com.javafse.week1.designpatterns.singleton.SingletonPatternExample
java -cp out com.javafse.week1.designpatterns.factory.FactoryMethodPatternExample
java -cp out com.javafse.week1.dsa.search.ECommerceSearchExample
java -cp out com.javafse.week1.dsa.forecasting.FinancialForecastingExample
```

