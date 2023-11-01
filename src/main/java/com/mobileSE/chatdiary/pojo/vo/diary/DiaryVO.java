package com.mobileSE.chatdiary.pojo.vo.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DiaryVO {
    private Long id;
    private String title;
    private String content;
    private String timestamp;
    private String position;
    private String type;


}