package com.wecho.core.aop.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyMain {
    public static void main(String[] args) {
        TestInterface testInterface = new TestAop();
        InvocationHandler invocationHandler = new LoggerHandler(testInterface);

        TestInterface proxy = (TestInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), testInterface.getClass().getInterfaces(), invocationHandler);
        proxy.test();
    }
}