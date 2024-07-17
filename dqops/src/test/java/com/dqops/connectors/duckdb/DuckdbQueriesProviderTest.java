package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
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
    void provideCreateSecretQuery_forAzureConnectionString_createsQueryWithFullScopePath() {
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

    @Test
    void provideCreateSecretQuery_forAzureCredentialChain_createsQueryWithFullScopePath() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.credential_chain);
        String scope = "az://container-name";

        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER CREDENTIAL_CHAIN,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name'");
        assertThat(lineSplittedResult.get(5)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forAzureServicePrincipal_createsQueryWithFullScopePath() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.service_principal);
        String scope = "az://container-name";

        connectionSpec.getDuckdb().setTenantId("tenantid");
        connectionSpec.getDuckdb().setClientId("clientid");
        connectionSpec.getDuckdb().setClientSecret("clientsecret");
        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER SERVICE_PRINCIPAL,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*TENANT_ID 'tenantid',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*CLIENT_ID 'clientid',");
        assertThat(lineSplittedResult.get(5)).matches("\\s*CLIENT_SECRET 'clientsecret',");
        assertThat(lineSplittedResult.get(6)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(7)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name'");
        assertThat(lineSplittedResult.get(8)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forAzureConnectionString_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.connection_string);
        String scope = "az://duckdbtest.blob.core.windows.net/container-name";
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

    @Test
    void provideCreateSecretQuery_forAzureCredentialChain_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.credential_chain);
        String scope = "az://duckdbtest.blob.core.windows.net/container-name";

        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER CREDENTIAL_CHAIN,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name'");
        assertThat(lineSplittedResult.get(5)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forAzureServicePrincipal_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.service_principal);
        String scope = "az://duckdbtest.blob.core.windows.net/container-name";

        connectionSpec.getDuckdb().setTenantId("tenantid");
        connectionSpec.getDuckdb().setClientId("clientid");
        connectionSpec.getDuckdb().setClientSecret("clientsecret");
        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER SERVICE_PRINCIPAL,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*TENANT_ID 'tenantid',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*CLIENT_ID 'clientid',");
        assertThat(lineSplittedResult.get(5)).matches("\\s*CLIENT_SECRET 'clientsecret',");
        assertThat(lineSplittedResult.get(6)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(7)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name'");
        assertThat(lineSplittedResult.get(8)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forAzureWithFullPathAndPrefix_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.credential_chain);
        String scope = "az://duckdbtest.blob.core.windows.net/container-name/prefix";

        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER CREDENTIAL_CHAIN,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name/prefix'");
        assertThat(lineSplittedResult.get(5)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forAzureWithJustContainerAndPrefix_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.credential_chain);
        String scope = "az://container-name/prefix";

        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER CREDENTIAL_CHAIN,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name/prefix'");
        assertThat(lineSplittedResult.get(5)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forAzureWithJustContainerWithTrailingSlash_createsQuery() { // todo
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.credential_chain);
        String scope = "az://container-name/";

        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER CREDENTIAL_CHAIN,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name'");
        assertThat(lineSplittedResult.get(5)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forAzureFullPathEndingContainerNameWithSlash_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnAzure(DuckdbFilesFormatType.csv, AzureAuthenticationMode.credential_chain);
        String scope = "az://duckdbtest.blob.core.windows.net/container-name/";

        connectionSpec.getDuckdb().setAccountName("duckdbtest");

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        List<String> lineSplittedResult = Arrays.stream(createSecretQuery.split("\n")).collect(Collectors.toList());

        assertThat(lineSplittedResult.get(0)).matches("CREATE SECRET s_[0-9a-f]* \\(");
        assertThat(lineSplittedResult.get(1)).matches("\\s*TYPE AZURE,\\s*");
        assertThat(lineSplittedResult.get(2)).matches("\\s*PROVIDER CREDENTIAL_CHAIN,");
        assertThat(lineSplittedResult.get(3)).matches("\\s*ACCOUNT_NAME 'duckdbtest',");
        assertThat(lineSplittedResult.get(4)).matches("\\s*SCOPE 'az://duckdbtest.blob.core.windows.net/container-name/'");
        assertThat(lineSplittedResult.get(5)).matches("\\);");
    }

    @Test
    void provideCreateSecretQuery_forGcs_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFilesOnGoogle(DuckdbFilesFormatType.csv);
        String scope = "gs://path";
        String secretName = "s_" + DuckdbQueriesProvider.calculateSecretHex(scope);
        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);

        Assertions.assertTrue(createSecretQuery.contains("CREATE SECRET " + secretName));

        assertThat(createSecretQuery).matches(
                "CREATE SECRET s_[0-9a-f]* \\(\\s*" +
                        "TYPE GCS,\\s*" +
                        "KEY_ID 'gcs_example_key_id',\\s*" +
                        "SECRET 'gcs_example_secret',\\s*" +
                        "SCOPE 'gs://path'\\s*" +
                        "\\);"
        );
    }

}