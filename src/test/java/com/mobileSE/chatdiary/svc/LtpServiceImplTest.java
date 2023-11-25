package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.pojo.vo.ApiData.ltp.LtpResponse;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import com.mobileSE.chatdiary.svc.service.LtpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LtpServiceImplTest {

    @Autowired
    private LtpService ltpService;


    @Test
    public void testSad() throws UnsupportedEncodingException {
        System.out.println(ltpService.callLtpApi("我成绩很差"));
    }
    @Test
    public void testHappy() throws UnsupportedEncodingException {
        System.out.println(ltpService.callLtpApi("我成绩好得不得了,我超级开心, 开心死了"));
    }
}