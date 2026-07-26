package com.javafse.week1.designpatterns.factory;

public class FactoryMethodPatternExample {
    public static void main(String[] args) {
        System.out.println("\n--- Exercise 2: Factory Method Pattern ---");

        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        Document wordDocument = wordFactory.createDocument();
        Document pdfDocument = pdfFactory.createDocument();
        Document excelDocument = excelFactory.createDocument();

        wordDocument.open();
        pdfDocument.open();
        excelDocument.open();
    }
}

