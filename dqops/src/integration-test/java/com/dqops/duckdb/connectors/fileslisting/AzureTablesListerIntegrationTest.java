package com.dqops.duckdb.connectors.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.TablesListerProviderObjectMother;
import com.dqops.connectors.duckdb.fileslisting.azure.AzureTablesLister;
import com.dqops.connectors.storage.azure.AzureAuthenticationMode;
import com.dqops.core.secrets.DevelopmentCredentialsSecretNames;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderImpl;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AzureTablesListerIntegrationTest extends BaseTest {

    private DuckdbParametersSpec duckdbParametersSpec;
    private String schemaName = "files";
    private AzureTablesLister sut;
    private SecretValueProviderImpl secretValueProvider;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        secretValueLookupContext = new SecretValueLookupContext(null);

        this.sut = (AzureTablesLister) TablesListerProviderObjectMother.getProvider().createTablesLister(DuckdbStorageType.azure);

        this.duckdbParametersSpec = new DuckdbParametersSpec();

        duckdbParametersSpec.setFilesFormatType(DuckdbFilesFormatType.csv);
    }

    @Test
    void listTables_straightlyFromBucket_returnFileAndFolder() {
        duckdbParametersSpec.setAzureAuthenticationMode(AzureAuthenticationMode.connection_string);

        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container/");
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_STORAGE_CONNECTION_STRING, secretValueLookupContext));

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName, null, 300);

        assertThat(sourceTableModels)
                .hasSizeGreaterThanOrEqualTo(2)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .contains(
                        "string_test_data.csv",
                        "test-folder-1"
                );
    }

    @Test
    void listTables_straightlyFromBucketWithNoTrailingSlash_returnFileAndFolder() {
        duckdbParametersSpec.setAzureAuthenticationMode(AzureAuthenticationMode.connection_string);

        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container");
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_STORAGE_CONNECTION_STRING, secretValueLookupContext));

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName, null, 300);

        assertThat(sourceTableModels)
                .hasSizeGreaterThanOrEqualTo(2)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .contains(
                        "string_test_data.csv",
                        "test-folder-1"
                );
    }

    @Test
    void listTables_fromBucketPrefixWithSingleFile_returnOnlyOneFile() {
        duckdbParametersSpec.setAzureAuthenticationMode(AzureAuthenticationMode.connection_string);

        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container/test-folder-1/data-set-1/");
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_STORAGE_CONNECTION_STRING, secretValueLookupContext));

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName, null, 300);

        assertThat(sourceTableModels)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .containsExactly(
                        "continuous_days_one_row_per_day.csv"
                );
    }

    @Test
    void listTables_withUseOfServicePrincipal_haveAccess() {
        duckdbParametersSpec.setAzureAuthenticationMode(AzureAuthenticationMode.service_principal);
        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container/test-folder-1/data-set-1/");

        duckdbParametersSpec.setTenantId(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_SERVICE_PRINCIPAL_TENANT_ID, secretValueLookupContext));
        duckdbParametersSpec.setClientId(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_SERVICE_PRINCIPAL_CLIENT_ID, secretValueLookupContext));
        duckdbParametersSpec.setClientSecret(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_SERVICE_PRINCIPAL_CLIENT_SECRET, secretValueLookupContext));
        duckdbParametersSpec.setAccountName(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_STORAGE_ACCOUNT_NAME, secretValueLookupContext));

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName, null, 300);

        assertThat(sourceTableModels)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .containsExactly(
                        "continuous_days_one_row_per_day.csv"
                );
    }

    @Test
    void listTables_pathUseDomain_returnFileAndFolder() {
        duckdbParametersSpec.setAzureAuthenticationMode(AzureAuthenticationMode.connection_string);
        String accountName = secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_STORAGE_ACCOUNT_NAME, secretValueLookupContext);
        duckdbParametersSpec.getDirectories().put(schemaName, "az://" + accountName + ".blob.core.windows.net/duckdb-container/");
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_STORAGE_CONNECTION_STRING, secretValueLookupContext));

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName, null, 300);

        assertThat(sourceTableModels)
                .hasSizeGreaterThanOrEqualTo(2)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .contains(
                        "string_test_data.csv",
                        "test-folder-1"
                );
    }

}