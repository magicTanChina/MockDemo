package com.magictan.mockdemo.model;

public class Node {
    private int num;
    private String name;

    public Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    public Node(int num) {
        this.num = num;
    }

    public Node(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
