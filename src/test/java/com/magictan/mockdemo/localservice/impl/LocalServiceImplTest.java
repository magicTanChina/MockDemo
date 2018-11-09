package com.magictan.mockdemo.localservice.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.impl.RemoteServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LocalServiceImplTest {

    @InjectMocks
    private LocalServiceImpl localService;
    @Mock
    private RemoteServiceImpl remoteService;

    /*@Before
    public void setUp() throws Exception {
        localService = new LocalServiceImpl();
        remoteService = Mockito.mock(RemoteServiceImpl.class);
        Whitebox.setInternalState(localService, "remoteService", remoteService);
    }*/

    /**
     * 普通mock
     */
    @Test
    public void test() {
        //test commit
        Node target = new Node(1, "target");
        Mockito.when(remoteService.getRemoteNode(1)).thenReturn(target);
        Node result = localService.getRemoteNode(1);
        assertEquals(target, result);
        assertEquals(1, result.getNum());
        assertEquals("target", result.getName());
    }
}