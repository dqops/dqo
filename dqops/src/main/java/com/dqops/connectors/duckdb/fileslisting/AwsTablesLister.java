package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.metadata.sources.PhysicalTableName;
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
import java.util.stream.Collectors;

/**
 * Used to retrieve the list of files from AWS s3 that are used as tables by duckdb's listTables.
 */
@Slf4j
public class AwsTablesLister {

    /**
     * Returns list of SourceTableModel from remote storage - AWS s3
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @return The list of SourceTableModel.
     */
    public static List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName){
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
     * Transforms list of files available in s3 to list of SourceTableModel and filters to valid objects that can be directories or files with a specific extension.
     * @param duckdb DuckdbParametersSpec
     * @param files List of files from s3
     * @param schemaName Name of schema
     * @return The list of SourceTableModel
     */
    public static List<SourceTableModel> filterAndTransform(DuckdbParametersSpec duckdb, List<String> files, String schemaName){
        List<SourceTableModel> sourceTableModels = files.stream()
                .filter(file -> isFolderOrFileOfValidExtension(file, duckdb.getSourceFilesType()))
                .map(file -> {
                        String cleanedFilePath = file.endsWith("/") ? file.substring(0, file.length() - 1) : file;
                        String fileName = cleanedFilePath.substring(cleanedFilePath.lastIndexOf("/") + 1);
                        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, fileName);
                        SourceTableModel sourceTableModel = new SourceTableModel(schemaName, physicalTableName);
                        return sourceTableModel;
                }).collect(Collectors.toList());
        return sourceTableModels;
    }

    /**
     * Verifies whether the given file name is a valid file name that DuckDB can work with.
     * @param storageObjectName The file name in storage
     * @param filesType File type used for extension matching.
     * @return Whether the file is valid
     */
    private static boolean isFolderOrFileOfValidExtension(String storageObjectName, DuckdbSourceFilesType filesType){
        String sourceFilesTypeString = filesType.toString();
        return storageObjectName.toLowerCase().endsWith("." + sourceFilesTypeString)
                || storageObjectName.toLowerCase().endsWith(".gz")
                || storageObjectName.endsWith("/");
    }

    /**
     * Lists objects form AWS S3.
     * @param s3 S3Client
     * @param s3Uri S3Uri
     * @return The list of objects in S3
     */
    private static List<String> listBucketObjects(S3Client s3, S3Uri s3Uri) {
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
                paths.add(content.key());
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
