package com.magictan.mockdemo.localservice.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.magictan.mockdemo.localservice.ILocalService;
import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.IRemoteService;

public class LocalServiceImpl implements ILocalService {

    @Autowired
    private IRemoteService remoteService;

    @Override
    public Node getRemoteNode(int num) {
        return remoteService.getRemoteNode(num);
    }

}
