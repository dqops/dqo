package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbStorageType;
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

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);

        this.sut = (AzureTablesLister)TablesListerProvider.createTablesLister(DuckdbStorageType.azure);

        this.duckdbParametersSpec = new DuckdbParametersSpec();
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.AZURE_STORAGE_CONNECTION_STRING, secretValueLookupContext));

        duckdbParametersSpec.setFilesFormatType(DuckdbFilesFormatType.csv);
    }

    @Test
    void listTables_straightlyFromBucket_returnFileAndFolder() {
        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container/");

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName);

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
        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container");

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName);

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
        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container/test-folder-1/data-set-1/");

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName);

        assertThat(sourceTableModels)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .containsExactly(
                        "continuous_days_one_row_per_day.csv"
                );
    }

    @Test
    void listTables_withUseOfServicePrincipal_haveAccess() {
        duckdbParametersSpec.getDirectories().put(schemaName, "az://duckdb-container/test-folder-1/data-set-1/");

        duckdbParametersSpec.setAzureAuthenticationMode(AzureAuthenticationMode.service_principal);
        duckdbParametersSpec.setTenantId(DevelopmentCredentialsSecretNames.AZURE_SERVICE_PRINCIPAL_TENANT_ID);
        duckdbParametersSpec.setClientId(DevelopmentCredentialsSecretNames.AZURE_SERVICE_PRINCIPAL_CLIENT_ID);
        duckdbParametersSpec.setClientSecret(DevelopmentCredentialsSecretNames.AZURE_SERVICE_PRINCIPAL_CLIENT_SECRET);
        duckdbParametersSpec.setAccountName(DevelopmentCredentialsSecretNames.AZURE_STORAGE_ACCOUNT_NAME);

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName);

        assertThat(sourceTableModels)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .containsExactly(
                        "continuous_days_one_row_per_day.csv"
                );
    }

}