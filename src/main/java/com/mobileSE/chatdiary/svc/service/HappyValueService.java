package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.vo.table.HappyValueVO;

import java.util.List;

public interface HappyValueService {
    List<HappyValueVO>  getHappyValuesList(Long timestamp,Long AuthorId);
}
