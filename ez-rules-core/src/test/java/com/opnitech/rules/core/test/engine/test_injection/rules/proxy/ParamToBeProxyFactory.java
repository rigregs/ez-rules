package com.opnitech.rules.core.test.engine.test_injection.rules.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ParamToBeProxyFactory {

    public static Object createProxy(final ParamToBeProxy paramToBeProxy) {

        return Proxy.newProxyInstance(ParamToBeProxyFactory.class.getClassLoader(), new Class[]
            {
                ParamToBeProxyInterface1.class,
                ParamToBeProxyInterface2.class
            }, new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    return paramToBeProxy.getValue();
                }
            });
    }
}
