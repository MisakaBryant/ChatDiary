package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.entity.DiaryImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ImageService {
    void uploadDiaryImageByDate(MultipartFile image, Date timestamp, Long diaryId);

    void uploadUserImageByUserId(MultipartFile image, Long userId);

    String uploadImageByName(MultipartFile image, String filename);

    List<DiaryImageEntity> getImageByDiaryId(Long id);
}
