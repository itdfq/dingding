package com.itdfq.dingding.domain.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: QianMo
 * @date: 2022/1/20 11:06
 * @mark:
 */
@Data
public class BaseParam implements Serializable {
    /**
     * 必填(这里不能用驼峰)
     */
    @JSONField(name = "msgtype")
    private String msgType;
    /**
     * accessToken
     */
    private String accessToken;
    /**
     * 密匙
     */
    private String secret;

    private At at;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class At implements Serializable {
        /**
         * 被@人的手机号
         * <p>
         * 在content里添加@人的手机号，只有在群里才能被@，否则会被脱敏
         */
        private List<String> atMobiles;

        /**
         * 被@人的用户UserId
         */
        private List<String> atUserIds;
        /**
         * 是否@所有人
         */
        private Boolean isAtAll;
    }
}
