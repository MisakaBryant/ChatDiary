package com.mobileSE.chatdiary.pojo.vo.table;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HappyValueVO {
    private LocalDate startDate;
    private Integer value;
}
