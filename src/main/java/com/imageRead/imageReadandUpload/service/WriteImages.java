package com.imageRead.imageReadandUpload.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class WriteImages {
    private String path = "C:\\Users\\hifza\\IdeaProjects\\imageReadandUpload\\TestData\\";

    public void writeImagestoFolder(MultipartFile[] images) throws IOException {
        //System.out.println("Hifaan " + images.length);
        for(int i=0; i<images.length; i++){
            File outputImage = new File(path + i + images[i].getOriginalFilename());
            outputImage.createNewFile();
            FileOutputStream impageOutWrite = new FileOutputStream(outputImage);
            impageOutWrite.write(images[i].getBytes());
            impageOutWrite.close();
        }
    }

}
