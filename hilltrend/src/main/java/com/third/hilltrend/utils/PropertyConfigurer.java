package com.third.hilltrend.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

@Slf4j
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static final String ENCRYKEY = "5ZIcoaQqMj0=";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

        String userName = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        if (StringUtils.isNotBlank(userName)) {
            props.setProperty("jdbc.username", decodeBuffer(userName));
        }
        if (StringUtils.isNotBlank(password)) {
            props.setProperty("jdbc.password", decodeBuffer(password));
        }
        super.processProperties(beanFactoryToProcess, props);
    }

    public static String decodeBuffer(String plainText) {
        try {
            return new String(DESCoder.decrypt(plainText, ENCRYKEY));
        } catch (Exception e) {
//            log.warn("PlaceProperties:" + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String a = DESCoder.encryptBASE64(DESCoder.encrypt("passport".getBytes(), ENCRYKEY));
        System.out.println(a);
        System.out.println(new String(DESCoder.decrypt(a, ENCRYKEY)));
    }

}