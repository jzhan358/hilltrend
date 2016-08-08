package com.third.hilltrend.api.common.client;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

public class ZDHessianProxyFactoryBean extends HessianProxyFactoryBean {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.invoke(invocation);
    }
}
