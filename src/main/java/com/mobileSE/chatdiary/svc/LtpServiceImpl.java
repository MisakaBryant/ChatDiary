package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.controller.ApiController.LtpController;
import com.mobileSE.chatdiary.svc.service.LtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
@Slf4j
public class LtpServiceImpl implements LtpService {
    @Autowired
    private LtpController ltpController;
    private static final String Ltp_Service_TYPE = "dependent";

    @Value("${ltpapi.appid}")
    private String appId;
    @Value("${ltpapi.apikey}")
    private String apiKey;

    public String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    @Override
    public String callLtpApi(String text) {

        String curTime = System.currentTimeMillis() / 1000L + "";
        String paramBase64 = "eyJ0eXBlIjoiZGVwZW5kZW50In0=";
        String checkSum = DigestUtils.md5Hex(apiKey + curTime + paramBase64);
        String body = "text=" + urlEncode(text);
        return ltpController.call(appId, curTime, paramBase64, checkSum, body);
    }
}
