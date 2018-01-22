/*package com.wecho.core.test.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BookAspect {

    @Pointcut("execution(* com.wecho.core.test.aspect.ReadBook(..))")
    public void ReadBook(){}

    @Before("ReadBook()")
    public void beforeOpen(){
        System.out.println("before read book");
    }

    @After("ReadBook()")
    public void afterRead(){
        System.out.println("close book");
    }
}*/
