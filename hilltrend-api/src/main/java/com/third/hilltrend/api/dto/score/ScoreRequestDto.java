package com.third.hilltrend.api.dto.score;

import com.third.hilltrend.api.base.SerialDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2016/8/7.
 */
@NoArgsConstructor
@Data
public class ScoreRequestDto extends SerialDto {
    private long userId;
    private String opType;
}
