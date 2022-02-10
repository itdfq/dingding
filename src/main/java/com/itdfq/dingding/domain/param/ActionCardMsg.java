package com.itdfq.dingding.domain.param;

import com.alibaba.fastjson.annotation.JSONField;

import com.itdfq.dingding.enums.DingMsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: QianMo
 * @date: 2022/1/20 15:08
 * @mark:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionCardMsg extends BaseParam implements Serializable {
    {
        this.setMsgType(DingMsgTypeEnum.ACTION_CARD.getMsgtype());
    }

    private ActionCard actionCard;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActionCard {
        private String title;
        private String text;
        /**
         * 0：按钮竖直排列
         * 1：按钮横向排列
         */
        private String btnOrientation;
        /**
         * 单个按钮标题
         */
        private String singleTitle;
        @JSONField(name = "singleURL")
        private String singleUrl;
        /**
         * 多个按钮
         */
        private List<Btns> btns;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Btns {
        private String title;
        @JSONField(name = "actionURL")
        private String actionUrl;
    }
}
