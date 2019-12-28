package com.imageRead.imageReadandUpload.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.imageRead.imageReadandUpload.Utils.Constants;
import com.imageRead.imageReadandUpload.service.AwsService;
import com.imageRead.imageReadandUpload.service.WriteImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("upload/signature")
public class ImageUploadController {

    @Autowired
    WriteImages writeImages;

    @Autowired
    AwsService awsService;


    @PostMapping(value = "/localcopy", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> getUploadedImages(@RequestParam("image") MultipartFile[] images) throws IOException {
        writeImages.writeImagestoFolder(images);
        return new ResponseEntity<>("Uploaded", HttpStatus.OK);
    }

    @PostMapping (value = "/s3copy", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadedImageToS3(@RequestParam("image") MultipartFile image) throws IOException {
        awsService.uploadImagetoS3(image);
        return new ResponseEntity<>("Uploaded", HttpStatus.OK);
    }


}
