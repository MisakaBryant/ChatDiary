package com.mobileSE.chatdiary.pojo.vo.ApiData.ltp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class LtpRequest {
    @JsonProperty("text")
    private String text;
}