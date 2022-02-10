package com.itdfq.dingding.domain.param;

import com.itdfq.dingding.enums.DingMsgTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: QianMo
 * @date: 2022/1/20 14:44
 * @mark:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkMsg extends BaseParam implements Serializable {
    {
        this.setMsgType(DingMsgTypeEnum.LINK.getMsgtype());
    }

    private Link link;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Link implements Serializable {
        /**
         * 消息内容，过长会展示部分
         * 必填
         */
        private String text;
        /**
         * 消息标题
         * 必填
         */
        private String title;
        /**
         * 图片地址
         * 非必填
         */
        private String picUrl;
        /**
         * 点击跳转URL
         * <p/>
         * 移动端：在钉钉客户端内打开
         * PC:
         * 默认侧边栏
         * 外部浏览器打开：https://open.dingtalk.com/document/app/message-link-description?spm=ding_open_doc.document.0.0.69cf227d6jp8Fx#section-7w8-4c2-9az
         */
        private String messageUrl;
    }
}
