package com.wecho.core.test.cglib;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;

public class TestCglib {
    @Test
    public void testCglib(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibDemo.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("before call "+method);
            Object result = methodProxy.invokeSuper(o,objects);
            System.out.println("after call "+method);
            return result;
        });
        CglibDemo cglibDemoProxy = (CglibDemo) enhancer.create();
        cglibDemoProxy.sayHello();
        System.out.println(cglibDemoProxy);
    }
}