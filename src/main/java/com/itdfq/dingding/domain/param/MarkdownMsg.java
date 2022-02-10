package com.itdfq.dingding.domain.param;

import com.alibaba.fastjson.annotation.JSONField;

import com.itdfq.dingding.enums.DingMsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: QianMo
 * @date: 2022/1/20 14:53
 * @mark:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkdownMsg extends BaseParam implements Serializable {
    {
        this.setMsgType(DingMsgTypeEnum.MARKDOWN.getMsgtype());
    }

    @JSONField(name = "markdown")
    private MarkDown markDown;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MarkDown {
        private String title;
        private String text;
    }
}
