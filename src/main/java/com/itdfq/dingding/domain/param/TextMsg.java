package com.itdfq.dingding.domain.param;


import com.itdfq.dingding.enums.DingMsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: QianMo
 * @date: 2022/1/20 13:07
 * @mark:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMsg extends BaseParam implements Serializable {
    private Text text;

    {
        this.setMsgType(DingMsgTypeEnum.TEXT.getMsgtype());
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Text implements Serializable {
        private String content;
    }
}
