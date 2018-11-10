package com.magictan.mockdemo.exception;

public class MockException extends Exception {
    private String name;

    public MockException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
