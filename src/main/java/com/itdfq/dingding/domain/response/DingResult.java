package com.itdfq.dingding.domain.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: QianMo
 * @date: 2022/1/20 10:54
 * @mark:
 */
@Data
public class DingResult implements Serializable {
    private static final String SUCCESS_CODE = "0";
    private String errcode;
    private String errmsg;

    public Boolean isSuccess() {
        if (SUCCESS_CODE.equals(this.errcode)) {
            return true;
        }
        return false;
    }

    public static DingResult fail(String msg) {
        DingResult dingResult = new DingResult();
        dingResult.setErrcode("99999");
        dingResult.setErrmsg(msg);
        return dingResult;
    }

    public String getMsg() {
        return this.errmsg;
    }
}
