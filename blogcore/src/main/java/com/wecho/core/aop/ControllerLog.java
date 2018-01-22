package com.wecho.core.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ControllerLog {

    @Pointcut("execution(* com.wecho.core.controller.UserController.getUser(..))")
    public void doController(){}

    @Before("execution(* com.wecho.core.controller.UserController.getUser(..))")
    public void preLog(){
        System.out.println("preLog-------");
    }

    @After("execution(* com.wecho.core.controller.UserController.getUser(..))")
    public void afterLog(){
        System.out.println("afterLog-------");
    }
}
