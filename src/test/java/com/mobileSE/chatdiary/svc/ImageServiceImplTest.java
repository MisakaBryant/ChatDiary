package com.mobileSE.chatdiary.svc;

import com.mobileSE.chatdiary.svc.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@SpringBootTest
public class ImageServiceImplTest {
    @Autowired
    private ImageService imageService;

    @Test
    public void testUploadImage() throws IOException {
        File img = new File("C:\\Users\\Misaka\\Pictures\\Saved Pictures\\Mondstadt.jpeg");
        imageService.uploadDiaryImageByDate(new MockMultipartFile("img", img.getName(), "image/jpeg", new FileInputStream(img)), new Date(), 1L);
    }

}
