package com.imageRead.imageReadandUpload.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.imageRead.imageReadandUpload.Utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class AwsService {

    public void uploadImagetoS3(MultipartFile image) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials(Constants.ACCESS_KEY_ID, Constants.ACCESS_SEC_KEY);
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_NORTH_1)
                .build();

        //Create Bucket Name
        String bucketName = Constants.BUCKET_NAME;

        if(!s3client.doesBucketExistV2(bucketName)) {
            s3client.createBucket(bucketName);
        }

        //Create folder into Bucket
        String folderName = Constants.FOLDER_NAME;

        //Upload a image into Bucket
        //Read image using input Stream in bytes
        byte[] contents = IOUtils.toByteArray(image.getInputStream());
        InputStream stream = new ByteArrayInputStream(contents);

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contents.length);
        meta.setContentType("image/png");

        //File outputImage = new File(image.getOriginalFilename());
        s3client.putObject(new PutObjectRequest(bucketName, image.getOriginalFilename(),stream, meta)
             .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        System.out.println("Executed Successfully");
    }
}
