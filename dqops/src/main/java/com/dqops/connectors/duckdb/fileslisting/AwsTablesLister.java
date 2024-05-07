package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to retrieve the list of files from AWS s3 that are used as tables by duckdb's listTables.
 */
@Slf4j
public class AwsTablesLister extends RemoteTablesLister {

    /**
     * Returns list of SourceTableModel from remote storage - AWS s3
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @return The list of SourceTableModel.
     */
    public List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials
                .create(duckdb.getAwsAccessKeyId(), duckdb.getAwsSecretAccessKey());
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        S3Client s3Client = S3Client.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.of(duckdb.getRegion()))
                .build();

        String pathString = duckdb.getDirectories().get(schemaName);

        S3Utilities s3Utilities = s3Client.utilities();
        URI uri = URI.create(pathString);
        S3Uri s3Uri = s3Utilities.parseUri(uri);
        List<String> files = listBucketObjects(s3Client, s3Uri);
        s3Client.close();

        List<SourceTableModel> sourceTableModels = filterAndTransform(duckdb, files, schemaName);
        return sourceTableModels;
    }

    /**
     * Lists objects form AWS S3.
     * @param s3 S3Client
     * @param s3Uri S3Uri
     * @return The list of objects in S3
     */
    private List<String> listBucketObjects(S3Client s3, S3Uri s3Uri) {
        List<String> paths = new ArrayList<>();

        if(s3Uri.bucket().isEmpty()){
            return paths;
        }

        String bucketName = s3Uri.bucket().get();
        String prefixRaw = s3Uri.key().orElse("");
        String folderPrefix = prefixRaw + (prefixRaw.equals("") || prefixRaw.endsWith("/") ? "" : "/");

        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .delimiter("/")
                    .prefix(folderPrefix)
                    .build();

            ListObjectsResponse response = s3.listObjects(listObjects);
            for (S3Object content : response.contents()) {
                if(!content.key().equals(folderPrefix)){
                    paths.add(content.key());
                }
            }
            for (CommonPrefix commonPrefix : response.commonPrefixes()) {
                paths.add(commonPrefix.prefix());
            }
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
        return paths;
    }

}
