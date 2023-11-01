package com.mobileSE.chatdiary.pojo.vo.chat;

import lombok.Data;

import java.util.Date;

@Data
public class MessageVO {
    private String content;
    private String timestamp;
    private Boolean isUserMe;
}