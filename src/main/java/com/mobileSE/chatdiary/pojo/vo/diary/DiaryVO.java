package com.mobileSE.chatdiary.pojo.vo.diary;

import lombok.Data;

import java.util.Date;

@Data
public class DiaryVO {
    private Long id;
    private String title;
    private String content;
    private Date timestamp;
    private String position;
    private String type;
}