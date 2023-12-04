package com.mobileSE.chatdiary.pojo.vo.diary;


import lombok.Data;

import java.util.List;

@Data
public class DiaryGenVO {
    private Long id;
    private String date;
    private String content;
    private String title;
    private List<String> images;
}