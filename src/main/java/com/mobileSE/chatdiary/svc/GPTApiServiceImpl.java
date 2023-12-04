package com.mobileSE.chatdiary.svc;

import cn.hutool.json.JSONObject;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.dao.GPTApiDao;
import com.mobileSE.chatdiary.pojo.entity.APiType;
import com.mobileSE.chatdiary.pojo.entity.GPTApiEntity;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GPTApiServiceImpl implements GPTApiService {
    private final GPTApiDao gpApiDao;

    @Override
    public String getByStringUsingChatGPT(String input) {
        ChatGPT chatGPT = ChatGPT.builder().apiKeyList(gpApiDao.findAllByType(APiType.CHATGPT).stream().map(GPTApiEntity::getApiKey).collect(Collectors.toList())).timeout(900).apiHost("https://api.chatanywhere.com.cn/") //反向代理地址
                .build().init();

        Message system = Message.ofSystem("你现在是一名日记助手, 需要根据我的输入的一天的内容来生成日记, 请直接给出日记, 中文回答");
        Message message = Message.of(input);

        ChatCompletion chatCompletion = ChatCompletion.builder().model(ChatCompletion.Model.GPT_3_5_TURBO.getName()).messages(Arrays.asList(system, message)).maxTokens(3000).temperature(0.9).build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();

    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth() {
        String ak = "A74GELiSQGsUM2Mf5MbmZ22Q";
        String sk = "Gcfu9OkSNdVUX3IZH9hstQ4ZX52OG0xt";
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            log.info("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getStr("access_token");
        } catch (Exception e) {
            log.error("获取token失败！");
            throw new BizException(BizError.REQUEST_ERROR);
        }
    }

    @Override
    public String simpleQuestion(String input) {
        ChatGPT chatGPT = ChatGPT.builder().apiKeyList(gpApiDao.findAllByType(APiType.CHATGPT).stream().map(GPTApiEntity::getApiKey).collect(Collectors.toList())).timeout(900).apiHost("https://api.chatanywhere.com.cn/") //反向代理地址
                .build().init();

        Message system = Message.ofSystem("你现在是一只猫娘了, 来和我对话吧, 注意50字以内,中文回答");
        Message message = Message.of(input);

        ChatCompletion chatCompletion = ChatCompletion.builder().model(ChatCompletion.Model.GPT_3_5_TURBO.getName()).messages(Arrays.asList(system, message)).maxTokens(3000).temperature(0.9).build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }

    @Override
    public String simpleQuestionWithSystem(String systemStr, String input) {
        ChatGPT chatGPT = ChatGPT.builder().apiKeyList(gpApiDao.findAllByType(APiType.CHATGPT).stream().map(GPTApiEntity::getApiKey).collect(Collectors.toList())).timeout(900).apiHost("https://api.chatanywhere.com.cn/") //反向代理地址
                .build().init();

        Message system = Message.ofSystem(systemStr);
        Message message = Message.of(input);

        ChatCompletion chatCompletion = ChatCompletion.builder().model(ChatCompletion.Model.GPT_3_5_TURBO.getName()).messages(Arrays.asList(system, message)).maxTokens(3000).temperature(0.9).build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }

    @Override
    public String simpleQuestionUsingSchoolApi(String input) {
        ChatGPT chatGPT = ChatGPT.builder().apiKey("FAKE_KEY").timeout(900).apiHost("http://10.58.0.2:6677/v1/") //反向代理地址
                .build().init();

        Message system = Message.ofSystem("你现在是一只猫娘了, 来和我对话吧, 注意50字以内");
        Message message = Message.of(input);

        ChatCompletion chatCompletion = ChatCompletion.builder().model(ChatCompletion.Model.GPT_3_5_TURBO.getName()).messages(Arrays.asList(system, message)).maxTokens(3000).temperature(0.9).build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        return res.getContent();
    }
}
