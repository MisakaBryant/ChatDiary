package com.mobileSE.chatdiary.controller;

import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.pojo.vo.image.ImageUploadRequest;
import com.mobileSE.chatdiary.svc.service.ImageService;
import com.mobileSE.chatdiary.svc.service.GPTApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final GPTApiService apiService;

    @PostMapping("image")
    public CommonResponse<?> uploadImage(@RequestBody ImageUploadRequest request) {
        imageService.uploadDiaryImageByDate(request.getImage(), request.getTimestamp());
        return CommonResponse.success();
    }

}
