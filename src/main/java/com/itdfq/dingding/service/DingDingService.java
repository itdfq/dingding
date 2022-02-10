package com.itdfq.dingding.service;

import com.alibaba.fastjson.JSON;
import com.itdfq.dingding.domain.param.BaseParam;
import com.itdfq.dingding.domain.response.DingResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: QianMo
 * @date: 2022/1/20 10:17
 * @mark:
 */
@Service
@Slf4j
public class DingDingService {

    @Autowired
    private RestTemplate restTemplate;



    private static final String TIME_STAMP = "&timestamp=";
    private static final String SEND_URL = "https://oapi.dingtalk.com/robot/send?access_token=";
    /**
     * 时间戳key
     */
    private static final String DINGDING_KEY = "itdfq:dingding";

    private static final String TIME_STAP_KEY = "timeStrap";

    private static final String SIGN_KEY = "sign";

    /**
     * 加签
     * 当前时间戳，单位是毫秒，与请求调用时间误差不能超过1小时。
     *
     * @return
     */
    public Map<String, Object> sign( String secret) {
        try {
            Map<String, Object> map = new HashMap<>();
            Long timestamp = System.currentTimeMillis();
            map.put(TIME_STAP_KEY, timestamp);
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8.toString());
            map.put(SIGN_KEY, sign);
            //存储钉钉加密标签
            TimeExpiredPoolCache.put(DINGDING_KEY, map, TimeExpiredPoolCache.TIMER_MILLIS);
            log.info("timeStrap:{} sign:{}", timestamp, sign);
            return map;
        } catch (Exception e) {
            log.error("加签异常", e);
            return null;
        }
    }

    /**
     * 发送消息
     * 钉钉消息 一分钟只能发送20条 多了会被冻结10分钟
     *
     * @param msg
     * @return
     */
    public DingResult send(BaseParam msg) throws Exception {
        if (StringUtils.isBlank(msg.getAccessToken()) || StringUtils.isBlank(msg.getSecret())) {
            return DingResult.fail("token和秘钥不能为空");
        }
        Map<String, Object> map = TimeExpiredPoolCache.get(DINGDING_KEY);
        if (map == null) {
            map = sign( msg.getSecret());
        }
        String url = SEND_URL + msg.getAccessToken() + TIME_STAMP + map.get(TIME_STAP_KEY) + "&sign=" + map.get(SIGN_KEY);
        log.info("请求地址：{} 请求参数：{}", url, JSON.toJSONString(msg));
        return restTemplate.postForObject(url, JSON.toJSON(msg), DingResult.class);


    }

}
