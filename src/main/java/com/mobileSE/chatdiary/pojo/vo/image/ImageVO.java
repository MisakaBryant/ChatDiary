package com.mobileSE.chatdiary.pojo.vo.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageVO {
    private MultipartFile image;
    private String description;
}
