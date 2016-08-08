package com.third.hilltrend.api.common.dto;

import com.third.hilltrend.api.base.SerialDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 *
 */
public class HilltrendPageSearchDto extends SerialDto {

    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 页号
     */
    protected int pageNo = 1;

    /**
     * 每页数量
     */
    protected int pageSize;

    /**
     * 排序属性集合
     */
    protected List<HilltrendPageSearchDto> sortDtoList = new ArrayList<HilltrendPageSearchDto>();

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<HilltrendPageSearchDto> getSortDtoList() {
        return sortDtoList;
    }

    public void setSortDtoList(List<HilltrendPageSearchDto> sortDtoList) {
        this.sortDtoList = sortDtoList;
    }

    public void addSorts(HilltrendPageSearchDto... sortDtos) {
        for (HilltrendPageSearchDto sortDto : sortDtos) {
            this.sortDtoList.add(sortDto);
        }
    }

    public int getStart() {
        if (this.pageSize == 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        return (this.pageNo - 1) * this.pageSize;
    }

    public int getEnd() {
        return this.getStart() + this.pageSize;
    }

}
