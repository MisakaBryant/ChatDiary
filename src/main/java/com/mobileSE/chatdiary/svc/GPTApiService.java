package com.mobileSE.chatdiary.svc;

public interface GPTApiService {
    String getByStringUsingChatGPT(String input);
    String simpleQuestion(String input);
    String simpleQuestionUsingSchoolApi(String input);

}
