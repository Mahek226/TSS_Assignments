package com.tss.model;

public class MovieStoreEmptyException extends Exception {
    public MovieStoreEmptyException(String message) {
        System.out.println("Movie store is empty. Add new movies.");
    }
}
