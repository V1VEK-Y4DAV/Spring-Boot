package com.javafse.week1.designpatterns.singleton;

public class SingletonPatternExample {
    public static void main(String[] args) {
        System.out.println("\n--- Exercise 1: Singleton Pattern ---");

        Logger loggerOne = Logger.getInstance();
        Logger loggerTwo = Logger.getInstance();

        loggerOne.log("First logger object is being used.");
        loggerTwo.log("Second logger reference is being used.");

        if (loggerOne == loggerTwo) {
            System.out.println("Result: Both references point to the same Logger instance.");
        } else {
            System.out.println("Result: Different Logger instances were created.");
        }
    }
}

