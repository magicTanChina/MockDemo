package com.magictan.mockdemo.localservice.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.MockitoJUnitRunner;

import com.magictan.mockdemo.exception.MockException;
import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.impl.RemoteServiceImpl;

@RunWith(MockitoJUnitRunner.class) //让测试运行于Mockito环境
public class LocalServiceImplMockTest {

    @InjectMocks
    private LocalServiceImpl localService;
    @Mock
    private RemoteServiceImpl remoteService;
    @Captor
    private ArgumentCaptor<String> localCaptor;

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
    public void testMock() {
        Node target = new Node(1, "target");    //创建一个Node对象作为返回值
        when(remoteService.getRemoteNode(1)).thenReturn(target); //指定当remoteService.getRemoteNode(int)方法传入参数为1时返回target对象
        Node result = localService.getRemoteNode(1);    //调用我们的业务方法，业务方法内部调用依赖对象方法
        assertEquals(target, result);   //可以断言我们得到的返回值其实就是target对象
        assertEquals(1, result.getNum());   //具体属性和我们指定的返回值相同
        assertEquals("target", result.getName());   //具体属性和我们指定的返回值相同

        Node result2 = localService.getRemoteNode(2);   //未指定参数为2时对应的返回规则
        assertNull(result2);    //未指定时返回为null
    }

    /**
     * any系列方法指定多参数情况
     */
    @Test
    public void testAny() {
        Node target = new Node(1, "target");
        when(remoteService.getRemoteNode(anyInt())).thenReturn(target); //静态导入Mockito.when和ArgumentMatchers.anyInt后可以简化代码提升可读性

        Node result = localService.getRemoteNode(20); //上面指定了调用remoteService.getRemoteNode(int)时，不管传入什么参数都会返回target对象
        assertEquals(target, result);   //可以断言我们得到的返回值其实就是target对象
        assertEquals(1, result.getNum());   //具体属性和我们指定的返回值相同
        assertEquals("target", result.getName());   //具体属性和我们指定的返回值相同
    }

    /**
     * 指定mock多次调用返回值
     */
    @Test
    public void testMultipleReturn() {
        Node target1 = new Node(1, "target");
        Node target2 = new Node(1, "target");
        Node target3 = new Node(1, "target");
        when(remoteService.getRemoteNode(anyInt())).thenReturn(target1).thenReturn(target2).thenReturn(target3);
        //第一次调用返回target1、第二次返回target2、第三次返回target3

        Node result1 = localService.getRemoteNode(1); //第1次调用
        assertEquals(target1, result1);
        Node result2 = localService.getRemoteNode(2); //第2次调用
        assertEquals(target2, result2);
        Node result3 = localService.getRemoteNode(3); //第3次调用
        assertEquals(target3, result3);
    }

    /**
     * 指定mock对象已声明异常抛出的方法抛出受检查异常
     */
    @Test
    public void testCheckedException() {
        try {
            Node target = new Node(1, "target");
            when(remoteService.getRemoteNode("name")).thenReturn(target).thenThrow(new MockException(
                    "message", "exception")); //第一次调用正常返回，第二次则抛出一个Exception

            Node result1 = localService.getRemoteNode("name");
            assertEquals(target, result1); //第一次调用正常返回

            Node result2 = localService.getRemoteNode("name"); //第二次调用不会正常返回，会抛出异常
            assertEquals(target, result2);
        } catch (MockException e) {
            assertEquals("exception", e.getName()); //验证是否返回指定异常内容
            assertEquals("message", e.getMessage()); //验证是否返回指定异常内容
        }
    }

    /**
     * 指定mock对象未声明异常抛出的方法抛出运行时异常
     */
    @Test
    public void testRuntimeException() {
        Node target = new Node(1, "target");
        when(remoteService.getRemoteNode(1)).thenThrow(new RuntimeException("exception")); //指定调用时抛出一个运行时异常

        try {
            Node result = localService.getRemoteNode(1);
            assertEquals(target, result);
        } catch (RuntimeException e) {
            assertEquals("exception", e.getMessage());
        }
    }

    /**
     * 指定mock对象未声明异常抛出的方法抛出受检查异常，以下方法执行会报错
     */
    /*@Test
    public void testNotDefineCheckedException() {
        Node target = new Node(1, "target");
        when(remoteService.getRemoteNode(1)).thenThrow(new IOException("io exception"));

        try {
            Node result = localService.getRemoteNode(1);
            assertEquals(target, result);
        } catch (Exception e) {
            assertEquals("io exception", e.getMessage());
        }
    }*/

    /**
     * 校验mock对象和方法的调用情况
     */
    public void testVerify() {
        Node target = new Node(1, "target");
        when(remoteService.getRemoteNode(anyInt())).thenReturn(target);

        verify(remoteService, Mockito.never()).getRemoteNode(1); //mock方法未调用过

        localService.getRemoteNode(1);
        verify(remoteService, times(1)).getRemoteNode(anyInt()); //目前mock方法调用过1次

        localService.getRemoteNode(2);
        verify(remoteService, times(2)).getRemoteNode(anyInt()); //目前mock方法调用过2次
        verify(remoteService, times(1)).getRemoteNode(2); //目前mock方法参数为2只调用过1次
    }

    /**
     * mock对象调用真实方法
     */
    @Test
    public void testCallRealMethod() {
        when(remoteService.getRemoteNode(anyInt())).thenCallRealMethod(); //设置调用真实方法
        Node result = localService.getRemoteNode(1);

        assertEquals(1, result.getNum());
        assertEquals("Node from remote service", result.getName());
    }

    /**
     * 利用ArgumentCaptor捕获方法参数进行mock方法参数校验
     */
    @Test
    public void testCaptor() throws Exception {
        Node target = new Node(1, "target");
        when(remoteService.getRemoteNode(anyString())).thenReturn(target);

        localService.getRemoteNode("name1");
        localService.getRemoteNode("name2");
        verify(remoteService, atLeastOnce()).getRemoteNode(localCaptor.capture()); //设置captor

        assertEquals("name2", localCaptor.getValue()); //获取最后一次调用的参数
        List<String> list = localCaptor.getAllValues(); //按顺序获取所有传入的参数
        assertEquals("name1", list.get(0));
        assertEquals("name2", list.get(1));
    }

    /**
     * 校验mock对象0调用和未被验证的调用
     */
    @Test(expected = NoInteractionsWanted.class)
    public void testInteraction() {
        verifyZeroInteractions(remoteService); //目前还未被调用过，执行不报错

        Node target = new Node(1, "target");
        when(remoteService.getRemoteNode(anyInt())).thenReturn(target);

        localService.getRemoteNode(1);
        localService.getRemoteNode(2);
        verify(remoteService, times(2)).getRemoteNode(anyInt());
        // 参数1和2的两次调用都会被上面的anyInt()校验到，所以没有未被校验的调用了
        verifyNoMoreInteractions(remoteService);

        reset(remoteService);
        localService.getRemoteNode(1);
        localService.getRemoteNode(2);
        verify(remoteService, times(1)).getRemoteNode(1);
        // 参数2的调用不会被上面的校验到，所以执行会抛异常
        verifyNoMoreInteractions(remoteService);
    }
}