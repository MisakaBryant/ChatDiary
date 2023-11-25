package com.mobileSE.chatdiary.svc.service;

import org.springframework.web.multipart.MultipartFile;

public interface GPTApiService {
    String getByStringUsingChatGPT(String input);
    String simpleQuestion(String input);
    String simpleQuestionUsingSchoolApi(String input);
    String getImgDescription(MultipartFile image);
}