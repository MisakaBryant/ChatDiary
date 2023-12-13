package com.mobileSE.chatdiary.svc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    public Integer callLtpApi(String text) {

        String curTime = System.currentTimeMillis() / 1000L + "";
        String paramBase64 = "eyJ0eXBlIjoiZGVwZW5kZW50In0=";
        String checkSum = DigestUtils.md5Hex(apiKey + curTime + paramBase64);
        //支持到500字节,算一个中文4个字节
        String body = "text=" + urlEncode(truncateToBytes(text, 125));
        Gson gson = new Gson();
        String call = ltpController.call(appId, curTime, paramBase64, checkSum, body);
        log.info("callLtpApi: " + call + " , body: " + text);
        JsonObject jsonObject = gson.fromJson(call, JsonObject.class);
        double score = jsonObject.getAsJsonObject("data").get("score").getAsDouble() * 100;
        return Math.toIntExact(Math.round(score));
    }

    private static String truncateToBytes(String text, int length) {
        if (text.length() <= length) {
            return text;
        } else {
            return text.substring(0, length);
        }
    }
}
