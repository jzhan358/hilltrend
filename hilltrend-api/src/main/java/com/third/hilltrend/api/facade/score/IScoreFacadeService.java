package com.third.hilltrend.api.facade.score;

import com.third.hilltrend.api.common.dto.HilltrendResultDto;
import com.third.hilltrend.api.dto.score.ScoreRequestDto;
import com.third.hilltrend.api.dto.score.ScoreResponseDto;

/**
 * Created by Administrator on 2016/8/7.
 */
public interface IScoreFacadeService {

    /**
     *获取当前积分
     * @return
     */
    HilltrendResultDto<ScoreResponseDto> getScore(ScoreRequestDto requestDto);
}
