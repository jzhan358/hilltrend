package com.third.hilltrend.api.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/7.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

public class BaseDto extends SerialDto {
    protected Integer id;

    protected Date createTime;

    protected Date modifyTime;

    protected String operator;
}
