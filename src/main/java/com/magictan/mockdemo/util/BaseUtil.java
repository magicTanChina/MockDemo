package com.magictan.mockdemo.util;

import com.magictan.mockdemo.model.Node;

public class BaseUtil {

    public static Node staticMethod(int n) {
        return new Node(n);
    }

}
