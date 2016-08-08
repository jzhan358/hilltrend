package com.third.hilltrend.api.dto.enums;

/**
 * Created by Administrator on 2016/8/7.
 */
public enum GoodsClass {
    GOODS_CLASS_0("1级商品", 0),
    GOODS_CLASS_1("2级商品", 1);

    GoodsClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
