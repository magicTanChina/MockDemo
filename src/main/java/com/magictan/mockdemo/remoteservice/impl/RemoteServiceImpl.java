package com.magictan.mockdemo.remoteservice.impl;

import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.IRemoteService;

public class RemoteServiceImpl implements IRemoteService {

    @Override
    public Node getRemoteNode(int num) {
        return new Node(num, "Node from remote service");
    }

}
