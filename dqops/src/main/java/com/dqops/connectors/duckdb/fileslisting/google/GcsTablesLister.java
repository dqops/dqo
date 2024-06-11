package com.dqops.connectors.duckdb.fileslisting.google;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.fileslisting.aws.AwsConstants;
import com.dqops.connectors.duckdb.fileslisting.aws.AwsTablesLister;
import com.dqops.connectors.duckdb.fileslisting.RemoteTablesLister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.S3Utilities;

import java.net.URI;
import java.util.List;

/**
 * Used to retrieve the list of files from Google Cloud Storage that are used as tables by duckdb's listTables.
 */
@Slf4j
@Component
public class GcsTablesLister extends RemoteTablesLister {

    public static final String SERVICE_ENDPOINT = "https://storage.googleapis.com";
    private final AwsTablesLister awsTablesLister;


    public GcsTablesLister(AwsTablesLister awsTablesLister) {
        this.awsTablesLister = awsTablesLister;
    }

    /**
     * Returns list of SourceTableModel from remote storage - Google Cloud Storage
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @return The list of SourceTableModel.
     */
    @Override
    public List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName, String tableNameContains, int limit) {

        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials
                .create(duckdb.getAwsAccessKeyId(), duckdb.getAwsSecretAccessKey());
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        S3Client s3Client = S3Client.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.AP_NORTHEAST_1)  // any random region, it is not used but necessary AWS
                .endpointOverride(URI.create(SERVICE_ENDPOINT))
                .build();

        String pathString = duckdb.getDirectories().get(schemaName);
        if(!pathString.startsWith(GcsConstants.GCS_URI_PREFIX)){
            throw new RuntimeException("Invalid URI scheme part : " + pathString);
        }
        String pathStringWithS3Scheme = pathString.replace(GcsConstants.GCS_URI_PREFIX, AwsConstants.S3_URI_PREFIX);

        S3Utilities s3Utilities = s3Client.utilities();
        URI uri = URI.create(pathStringWithS3Scheme);
        S3Uri s3Uri = s3Utilities.parseUri(uri);
        List<String> files = awsTablesLister.listBucketObjects(s3Client, s3Uri);
        s3Client.close();

        List<SourceTableModel> sourceTableModels = filterAndTransform(duckdb, files, schemaName, tableNameContains, limit);
        return sourceTableModels;
    }

}
