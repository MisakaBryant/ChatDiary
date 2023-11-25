package com.mobileSE.chatdiary.controller.ApiController;

import com.mobileSE.chatdiary.pojo.vo.ApiData.ltp.LtpRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ltpapi", url = "https://ltpapi.xfyun.cn/v2")
public interface LtpController {
    @PostMapping(value = "/sa", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String call(@RequestHeader("X-Appid") String xAppid, @RequestHeader("X-CurTime") String xCurTime, @RequestHeader("X-Param") String xParam, @RequestHeader("X-CheckSum") String xCheckSum, @RequestBody String text);
}
