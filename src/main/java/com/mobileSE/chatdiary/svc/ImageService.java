package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.pojo.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ImageService {
    public void uploadImage(MultipartFile image, Date timestamp);
    public List<ImageEntity> getImageByDiaryId(Long id);
}
