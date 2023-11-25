package com.mobileSE.chatdiary.svc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
public class ImageServiceImplTest {
    @Autowired
    private ImageService imageService;

    @Test
    public void testUploadImage() throws IOException {
        File img = new File("C:\\Users\\Misaka\\Pictures\\Saved Pictures\\Noelle.jpeg");
        imageService.uploadImage(new MockMultipartFile("img", img.getName(), "image/jpeg", new FileInputStream(img)), new java.util.Date());
    }

}
