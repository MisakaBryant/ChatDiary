package com.mobileSE.chatdiary.pojo.vo.image;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ImageUploadRequest {
    private MultipartFile image;
    private Date timestamp;
}
