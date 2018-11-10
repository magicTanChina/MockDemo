package com.magictan.mockdemo.remoteservice;

import com.magictan.mockdemo.exception.MockException;
import com.magictan.mockdemo.model.Node;

public interface IRemoteService {
    public Node getRemoteNode(int num);

    public Node getRemoteNode(String name) throws MockException;

    public void doSometing();
}
