package com.third.hilltrend.api.common.dto;

/**
 * Created by Administrator on 2016/8/7.
 */
public enum CommonEnums {

    PC("PC", 0),
    ANDROID("ANDROID", 5),
    ISO("ISO", 10);

    CommonEnums(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return this.value;
    }
}
