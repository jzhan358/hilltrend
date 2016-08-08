package com.third.hilltrend.api.common.client;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.net.URL;

/**
 * Created by rui on 15/8/21.
 */
public class ZDHessianProxyFactory extends HessianProxyFactory {


    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());


    private String channel;

    @Override
    public Object create(Class api, URL url, ClassLoader loader) {
        if (channel == null) {
            throw new NullPointerException("channel must be config for ZDHessianProxyFactory.create()");
        }
        if (api == null){
            throw new NullPointerException("api must not be null for ZDHessianProxyFactory.create()");
        }
        ZDHessianProxy handler = new ZDHessianProxy(url, this, api,channel);
        return Proxy.newProxyInstance(loader, new Class[]{api, HessianRemoteObject.class}, handler);
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
