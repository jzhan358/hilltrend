package com.third.hilltrend.api.facade.goods;

import com.third.hilltrend.api.common.dto.HilltrendResultDto;

/**
 * Created by Administrator on 2016/8/7.
 */
public interface IGoodsFacadeService {

    /**
     * 商品
     * @return
     */
    HilltrendResultDto getGoodsDetail(String goodsId);
}
