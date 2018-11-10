package com.magictan.mockdemo.localservice.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.magictan.mockdemo.exception.MockException;
import com.magictan.mockdemo.localservice.ILocalService;
import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.IRemoteService;

public class LocalServiceImpl implements ILocalService {

    @Autowired
    private IRemoteService remoteService;

    @Override
    public Node getLocalNode(int num, String name) {
        return new Node(num, name);
    }

    @Override
    public Node getRemoteNode(int num) {
        return remoteService.getRemoteNode(num);
    }

    @Override
    public Node getRemoteNode(String name) throws MockException {
        try {
            return remoteService.getRemoteNode(name);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public void remoteDoSomething() {
        remoteService.doSometing();
    }
}
