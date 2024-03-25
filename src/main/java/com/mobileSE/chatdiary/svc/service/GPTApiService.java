package com.mobileSE.chatdiary.svc.service;


import reactor.core.publisher.Mono;

public interface GPTApiService {
    Mono<String> getByStringUsingChatGPT(String input);

    Mono<String> simpleQuestion(String input);

    String simpleQuestionWithSystem(String system, String input);

    String simpleQuestionUsingSchoolApi(String input);
}
