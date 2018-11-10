package com.magictan.mockdemo.remoteservice.impl;

import org.springframework.util.StringUtils;

import com.magictan.mockdemo.exception.MockException;
import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.IRemoteService;

public class RemoteServiceImpl implements IRemoteService {

    @Override
    public Node getRemoteNode(int num) {
        return new Node(num, "Node from remote service");
    }

    @Override
    public Node getRemoteNode(String name) throws MockException {
        if (StringUtils.isEmpty(name)) {
            throw new MockException("name不能为空", name);
        }
        return new Node(name);
    }

    @Override
    public void doSometing() {
        System.out.println("remote service do something!");
    }
}
