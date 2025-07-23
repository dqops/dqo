/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb.fileslisting.aws;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.fileslisting.RemoteTablesLister;
import com.dqops.connectors.storage.aws.AwsAuthenticationMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
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
@Component
public class AwsTablesLister extends RemoteTablesLister {

    /**
     * Creates an AWS credentials provider. When the access key id and secret key are present, they are used in basic authentication
     * mode. Otherwise, the default authentication provider chain is used.
     * @param duckdb DuckDb connection parameters.
     * @return AWS credentials provider.
     */
    public AwsCredentialsProvider createAwsCredentials(DuckdbParametersSpec duckdb)
    {
        if (duckdb.getAwsAuthenticationMode() == AwsAuthenticationMode.default_credentials &&
                (Strings.isNullOrEmpty(duckdb.getAwsAccessKeyId()) || Strings.isNullOrEmpty(duckdb.getAwsSecretAccessKey()))) {
            DefaultCredentialsProvider defaultCredentialsProvider = DefaultCredentialsProvider.builder()
                    .build();
            return defaultCredentialsProvider;
        }
        else {
            AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials
                    .create(duckdb.getAwsAccessKeyId(), duckdb.getAwsSecretAccessKey());
            StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
            return staticCredentialsProvider;
        }
    }

    /**
     * Returns list of SourceTableModel from remote storage - AWS s3
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @return The list of SourceTableModel.
     */
    @Override
    public List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName, String tableNameContains, int limit) {
        AwsCredentialsProvider awsCredentialsProvider = createAwsCredentials(duckdb);
        S3Client s3Client = S3Client.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.of(duckdb.getRegion()))
                .build();

        String pathString = duckdb.getDirectories().get(schemaName);

        S3Utilities s3Utilities = s3Client.utilities();
        URI uri = URI.create(pathString);
        S3Uri s3Uri = s3Utilities.parseUri(uri);
        List<String> files = listBucketObjects(s3Client, s3Uri);
        s3Client.close();

        List<SourceTableModel> sourceTableModels = filterAndTransform(duckdb, files, schemaName, tableNameContains, limit);
        return sourceTableModels;
    }

    /**
     * Lists objects form AWS S3.
     * @param s3 S3Client
     * @param s3Uri S3Uri
     * @return The list of objects in S3
     */
    public List<String> listBucketObjects(S3Client s3, S3Uri s3Uri) {
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
