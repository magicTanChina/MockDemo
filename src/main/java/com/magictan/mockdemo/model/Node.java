package com.magictan.mockdemo.model;

public class Node {
    private int num;
    private String name;

    public static Node getStaticNode() {
        return new Node(1, "static node");
    }

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
