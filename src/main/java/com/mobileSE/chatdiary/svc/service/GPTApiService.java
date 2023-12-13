package com.mobileSE.chatdiary.svc.service;


public interface GPTApiService {
    String getByStringUsingChatGPT(String input);

    String simpleQuestion(String input);

    String simpleQuestionWithSystem(String system, String input);

    String simpleQuestionUsingSchoolApi(String input);
}
