package com.wecho.core.reflcet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ReflectUtil {
    public static <T> T generator(Class<T> clazz, Map<String,Object> fieldValueMap) throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
        T object = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Method method;
            try {
                method = clazz.getMethod("set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1),field.getType());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                continue;
            }
            System.out.println(field.getName());
            method.invoke(object,fieldValueMap.get(field.getName()));
        }
        return object;
    }

    //private
}
