package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.vo.ApiData.ltp.LtpResponse;


import java.io.UnsupportedEncodingException;

public interface LtpService {
    public String callLtpApi(String text);
}
