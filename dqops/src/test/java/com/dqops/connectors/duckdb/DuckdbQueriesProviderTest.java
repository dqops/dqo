package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import com.dqops.connectors.storage.azure.AzureAuthenticationMode;
import com.dqops.metadata.sources.ConnectionSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DuckdbQueriesProviderTest extends BaseTest {

    @Test
    void provideCreateSecretQuery_forS3_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFilesOnS3(DuckdbFilesFormatType.csv);
        String scope = "s3://path";
        String secretName = "s_" + DuckdbQueriesProvider.calculateSecretHex(scope);
        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        Assertions.assertTrue(createSecretQuery.contains("CREATE SECRET " + secretName));

        assertThat(createSecretQuery).matches(
                "CREATE SECRET s_[0-9a-f]* \\(\\s*" +
                "TYPE S3,\\s*" +
                "KEY_ID 'aws_example_key_id',\\s*" +
                "SECRET 'aws_example_secret',\\s*" +
                "REGION 'eu-central-1',\\s*" +
                "SCOPE 's3://path'\\s*" +
                "\\);"
        );
    }

    @Test
    void provideCreateSecretQuery_forAzureConnectionString_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.connection_string);
        String scope = "az://container-name";
        connectionSpec.getDuckdb().setPassword(
                "DefaultEndpointsProtocol=https;AccountName=duckdbtest;AccountKey=some_key==;EndpointSuffix=core.windows.net");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*CONNECTION_STRING 'DefaultEndpointsProtocol=https;AccountName=duckdbtest;AccountKey=some_key==;EndpointSuffix=core.windows.net',");
        assertThat(lineSplittedResult.get(3)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name'");
        assertThat(lineSplittedResult.get(4)).matches("\\);");
    }

}