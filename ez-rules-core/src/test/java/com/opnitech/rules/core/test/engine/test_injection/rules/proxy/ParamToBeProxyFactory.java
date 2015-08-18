package com.opnitech.rules.core.test.engine.test_injection.rules.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ParamToBeProxyFactory {

    public static Object createProxy(final ParamToBeProxy paramToBeProxy, Class<?>[] interfaces) {

        return Proxy.newProxyInstance(ParamToBeProxyFactory.class.getClassLoader(), interfaces, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return paramToBeProxy.getValue();
            }
        });
    }
}
