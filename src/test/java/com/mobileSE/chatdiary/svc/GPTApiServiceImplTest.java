package com.mobileSE.chatdiary.svc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GPTApiServiceImplTest {
    @Autowired
    private GPTApiService gptApiService;


 //   @Test
//    public void testChatGpt() {
//        String result = gptApiService.simpleQuestion("你好");
//        System.out.println(result);
//    }
//    @Test
//    public void testSchool() {
//        String result = gptApiService.simpleQuestionUsingSchoolApi("你好");
//        System.out.println(result);
//    }

}