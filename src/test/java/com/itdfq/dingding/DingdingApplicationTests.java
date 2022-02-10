package com.itdfq.dingding;

import com.itdfq.dingding.domain.param.BaseParam;
import com.itdfq.dingding.domain.param.TextMsg;
import com.itdfq.dingding.domain.response.DingResult;
import com.itdfq.dingding.service.DingDingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DingdingApplicationTests {

    @Autowired
    private DingDingService dingDingService;
    private static final String SECRET = "SEC2f352ffa4fa8ab69ab95525ef841e042699a0be0198344bef2132f9dcee3a98811";

    private static final String ACCESS_TOKEN = "5dbf49ddfa5a0b9264fa79d0b71113d44dc0890b7ba98a9aa29c230be983c23f11";
    @Test
    void contextLoads() throws Exception {
        TextMsg textMsg = new TextMsg();
        textMsg.setSecret(SECRET);
        textMsg.setAccessToken(ACCESS_TOKEN);
        textMsg.setAt(new TextMsg.At(null, null, false));
        textMsg.setText(new TextMsg.Text("sdsarwhfnsduhfsbhfiadfdusfwefwe"));
        send(textMsg);
    }
    private void send(BaseParam baseParam) throws Exception {
        DingResult send = dingDingService.send(baseParam);
        System.out.println(send);
    }

}
