package com.third.hilltrend.facade.sys;

import com.third.hilltrend.api.facade.sys.IHealthFacadeService;

/**
 * Created by Administrator on 2016/8/7.
 */
public class HealthFacadeService implements IHealthFacadeService {
    public String execute(String message) {
        System.out.println(message);
        return message;
    }
}
