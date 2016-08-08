package com.third.hilltrend.api.common.dto;

import com.third.hilltrend.api.base.BaseDto;

/**
 * Created by Administrator on 2016/8/7.
 */
public class HilltrendSortDto extends BaseDto {

    /** 排序属性 */
    private String property;

    /** 升/降 */
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public HilltrendSortDto() {

    }

    public HilltrendSortDto(String property) {
        this.direction = Direction.ASC;
        this.property = property;
    }

    public HilltrendSortDto(String property, Direction direction) {
        this.direction = direction;
        this.property = property;
    }

    public enum Direction {
        ASC, DESC;
        Direction() {}
    }

}
