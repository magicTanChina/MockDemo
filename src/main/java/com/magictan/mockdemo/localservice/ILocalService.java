package com.magictan.mockdemo.localservice;

import com.magictan.mockdemo.exception.MockException;
import com.magictan.mockdemo.model.Node;

public interface ILocalService {

    public Node getRemoteNode(int num);

    public Node getRemoteNode(String name) throws MockException;

    public void remoteDoSomething();

    public Node getLocalNode(int num, String name);
}
