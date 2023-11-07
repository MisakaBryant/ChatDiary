package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.dao.GPTApiDao;
import com.mobileSE.chatdiary.pojo.entity.APiType;
import com.mobileSE.chatdiary.pojo.entity.GPTApiEntity;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GPTApiServiceImpl implements GPTApiService {
    private final GPTApiDao gpApiDao;

    @Override
    public String getByStringUsingChatGPT(String input) {
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKeyList(gpApiDao.findAllByType(APiType.CHATGPT).stream().map(GPTApiEntity::getApiKey).collect(Collectors.toList()))
                .timeout(900)
                .apiHost("https://api.chatanywhere.com.cn/") //反向代理地址
                .build()
                .init();

        Message system = Message.ofSystem("你现在是一名日记助手, 需要根据我的输入的一天的内容来生成日记, 请直接给出日记");
        Message message = Message.of(input);

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();

    }

    @Override
    public String simpleQuestion(String input) {
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKeyList(gpApiDao.findAllByType(APiType.CHATGPT).stream().map(GPTApiEntity::getApiKey).collect(Collectors.toList()))
                .timeout(900)
                .apiHost("https://api.chatanywhere.com.cn/") //反向代理地址
                .build()
                .init();

        Message system = Message.ofSystem("你现在是一只猫娘了, 来和我对话吧, 注意50字以内");
        Message message = Message.of(input);

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }

    @Override
    public String simpleQuestionUsingSchoolApi(String input) {
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey("FAKE_KEY")
                .timeout(900)
                .apiHost("http://10.58.0.2:6677/v1/") //反向代理地址
                .build()
                .init();

        Message system = Message.ofSystem("你现在是一只猫娘了, 来和我对话吧, 注意50字以内");
        Message message = Message.of(input);

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }
}
