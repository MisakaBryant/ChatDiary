package com.mobileSE.chatdiary.pojo.vo.chat;

import lombok.Data;

@Data
public class MessageVO {
    private String content;
    private String timestamp;
    private Boolean isUserMe;
}