package com.dqops.services.cloud.s3;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

// todo: descriptions
@Slf4j
public class AwsStorageOperator {

    public static List<String> listBucketObjects(S3Client s3, String bucketName, String prefix) {
        List<String> prefixes = new ArrayList<>();
        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .delimiter("/")
                    .prefix(prefix)
                    .build();

            ListObjectsResponse res = s3.listObjects(listObjects);
            List<CommonPrefix> commonPrefixes = res.commonPrefixes();
            for (CommonPrefix commonPrefix : commonPrefixes) {
                prefixes.add(commonPrefix.prefix());
            }
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
        return prefixes;
    }

}
