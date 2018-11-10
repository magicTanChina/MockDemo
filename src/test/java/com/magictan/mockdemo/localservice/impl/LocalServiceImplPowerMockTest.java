package com.magictan.mockdemo.localservice.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.magictan.mockdemo.model.Node;
import com.magictan.mockdemo.remoteservice.impl.RemoteServiceImpl;

@RunWith(PowerMockRunner.class) //让测试运行于PowerMock环境
public class LocalServiceImplPowerMockTest {

    @InjectMocks
    private LocalServiceImpl localService;
    @Mock
    private RemoteServiceImpl remoteService;

    @Test
    @PrepareForTest(LocalServiceImpl.class) //PrepareForTest修改local类的字节码以覆盖new的功能
    public void testNew() throws Exception {
        Node target = new Node(1, "target");
        //当传入任意int且name属性为"name"时，new对象返回为target
        //当参数条件使用了any系列方法时，剩余的参数都得使用相应的模糊匹配规则，如eq("name")代表参数等于"name"
        //剩余还有isNull(), isNotNull(), isA()等方法
        PowerMockito.whenNew(Node.class).withArguments(anyInt(), eq("name")).thenReturn(target);
        Node result = localService.getLocalNode(1, "name");
        assertEquals(1, result.getNum());
        assertEquals("target", result.getName());
    }

}