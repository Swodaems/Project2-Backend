package com.revature.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
@Service
public class PhotoUtil {
	public static String uploadPhoto(byte[] contents) {
		Regions region =Regions.US_EAST_2;
		String bucketName = "revaturemeadows";
		//System.out.println(System.getenv("S3AccessKeyID"));
		//System.out.println(System.getenv("S3SecretAccessKey"));
		BasicAWSCredentials cred = new BasicAWSCredentials((System.getenv("S3AccessKeyID")), System.getenv("S3SecretAccessKey"));
        try {
        	 AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                     .withRegion(region)
                     .withCredentials(new AWSStaticCredentialsProvider(cred))
                     .build();

        	 InputStream stream = new ByteArrayInputStream(contents);
        	 ObjectMetadata metadata = new ObjectMetadata();
        	 metadata.setContentLength(contents.length);
        	 metadata.setContentType("image/png");
        	 String fileName = (LocalDateTime.now()).toString();
        	 PutObjectRequest s3Put = new PutObjectRequest(bucketName, fileName, stream, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
        	 s3Client.putObject(s3Put);
        	 //AccessControlList acl = s3Client.getObjectAcl(bucketName, reimb_id);
        	 
        	 URL url = s3Client.getUrl(bucketName, fileName);
        	 return url.toString();
        	 
        }
	 catch (AmazonServiceException e) {
        // The call was transmitted successfully, but Amazon S3 couldn't process 
        // it, so it returned an error response.
        e.printStackTrace();
        return null;
    } catch (SdkClientException e) {
        // Amazon S3 couldn't be contacted for a response, or the client
        // couldn't parse the response from Amazon S3.
        e.printStackTrace();
        return null;
    }
	}
}
