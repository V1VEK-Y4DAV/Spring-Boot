package com.javafse.week1.designpatterns.factory;

public class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening a Word document.");
    }
}

