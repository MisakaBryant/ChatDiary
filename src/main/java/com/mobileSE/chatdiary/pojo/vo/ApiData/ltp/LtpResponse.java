package com.mobileSE.chatdiary.pojo.vo.ApiData.ltp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LtpResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("data")
    private LtpResponseData data;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("sid")
    private String sid;
}

class LtpResponseData {

    @JsonProperty("score")
    private double score;

    @JsonProperty("sentiment")
    private int sentiment;
}