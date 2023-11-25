package com.mobileSE.chatdiary.controller;

import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.pojo.vo.image.ImageUploadRequest;
import com.mobileSE.chatdiary.svc.ApiService;
import com.mobileSE.chatdiary.svc.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final ApiService apiService;

    @PostMapping("image")
    public CommonResponse<?> uploadImage(@RequestBody ImageUploadRequest request) {

        imageService.uploadImage(request.getImage(), request.getTimestamp());

        return CommonResponse.success();
    }

}
