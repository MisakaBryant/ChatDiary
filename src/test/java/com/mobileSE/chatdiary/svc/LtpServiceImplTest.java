package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.svc.service.LtpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
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
        System.out.println(ltpService.callLtpApi("今天吃了寿司，特别好吃。寿司店的氛围很棒，让我感觉仿佛置身于日本。而且店里的寿司师傅手艺非常了得，每一口寿司都散发着诱人的香气，让人垂涎欲滴。寿司店的老板是一位和蔼可亲的日本人，他下班时间比较早，但是每天都能看到他精心制作寿司的场景。我常常在他下班后，留在店里品尝美味的寿司，享受一份宁静的时光。今晚，他向我推荐了一种新口味的寿司，味道实在是太美妙了。我不禁想知道他为什么每天都下班这么早，可是又不影响店里的营业。或许，这就是日本人对生活的态度吧。"));
    }
}
