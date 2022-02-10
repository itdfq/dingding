package com.itdfq.dingding.enums;

/**
 * @author: QianMo
 * @date: 2022/1/20 11:08
 * @mark: 钉钉群发类型枚举
 */
public enum DingMsgTypeEnum {
    /**
     * Text类型
     */
    TEXT("text"),
    /**
     * Link类型
     */
    LINK("link"),
    /**
     * Markdown类型
     */
    MARKDOWN("markdown"),
    /**
     * 跳转ActionCard类型
     */
    ACTION_CARD("actionCard"),
    /**
     * FeedCard类型
     */
    FEED_CARD("feedCard");

    private String msgtype;

    DingMsgTypeEnum(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public String getMsgTypeByEnum(DingMsgTypeEnum typeEnum) {
        for (DingMsgTypeEnum test : values()) {
            if (test == typeEnum) {
                return test.getMsgtype();
            }
        }
        return null;
    }
}
