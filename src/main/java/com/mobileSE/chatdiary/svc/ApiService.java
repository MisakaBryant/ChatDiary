package com.mobileSE.chatdiary.svc;

import org.springframework.web.multipart.MultipartFile;

public interface ApiService {
    String getByStringUsingChatGPT(String input);
    String simpleQuestion(String input);
    String simpleQuestionUsingSchoolApi(String input);
    String getImgDescription(MultipartFile image);
}
