package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.svc.service.GPTApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GPTApiServiceImplTest {
    @Autowired
    private GPTApiService apiService;


    @Test
    public void testChatGpt() {
        String result = apiService.simpleQuestion("你好");
        System.out.println(result);
    }
//    @Test
//    public void testSchool() {
//        String result = gptApiService.simpleQuestionUsingSchoolApi("你好");
//        System.out.println(result);
//    }

}
