package com.magictan.mockdemo.localservice.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.impl.RemoteServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LocalServiceImplSpyTest {

    @InjectMocks
    private LocalServiceImpl localService;
    @Spy
    private RemoteServiceImpl remoteService;

    @Before
    public void setUp() throws Exception {
        localService = new LocalServiceImpl();
        remoteService = new RemoteServiceImpl();
        remoteService = Mockito.spy(remoteService);
        Whitebox.setInternalState(localService, "remoteService", remoteService);
    }

    /**
     * 普通spy
     */
    @Test
    public void testSpy() {
        Node target = new Node(1, "target");    //创建一个Node对象作为返回值
        Mockito.when(remoteService.getRemoteNode(1)).thenReturn(target); //指定当remoteService.getRemoteNode(int)方法传入参数为1时返回target对象
        Node result = localService.getRemoteNode(1);    //调用我们的业务方法，业务方法内部调用依赖对象方法
        assertEquals(target, result);   //可以断言我们得到的返回值其实就是target对象
        assertEquals(1, result.getNum());   //具体属性和我们指定的返回值相同
        assertEquals("target", result.getName());   //具体属性和我们指定的返回值相同

        Node result2 = localService.getRemoteNode(2);   //未指定参数为2时的调用规则，所以会直接调用真实对象，返回remote创建的节点
        assertEquals(2, result2.getNum());
        assertEquals("Node from remote service", result2.getName());    //remoteService创建Node对象时设置name属性为"Node from remote service"
    }
}