package com.third.hilltrend.api.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 */
public class HilltrendPageResultDto<T> extends HilltrendResultDto<T> {

    /** 总记录数 */
    protected Integer totalSize = 0;

    /** 总页数 */
    protected Integer totalPage = 0;

    /** 当前页数 */
    protected Integer pageNo = 1;

    /** 列表 */
    protected List<T> dataList = new ArrayList<>();

    public HilltrendPageResultDto() {
    }

    public HilltrendPageResultDto(List<T> data, Integer totalSize, Integer pageSize) {
        this.code = SUCCESS_CODE;
        this.totalPage =  (totalSize + pageSize - 1) / pageSize;
        this.dataList = data;
        this.totalSize = totalSize;
    }

    public HilltrendPageResultDto(List<T> data, Integer totalSize, Integer pageSize, int pageNo) {
        this(data, totalSize, pageSize);
        this.pageNo = pageNo;
    }

    public HilltrendPageResultDto(String code, String msg, List<T> data) {
        super(code,msg);
        this.dataList = data;
    }
}
