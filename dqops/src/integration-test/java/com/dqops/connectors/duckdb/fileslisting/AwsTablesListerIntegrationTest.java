package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbFilesFormatType;
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
class AwsTablesListerIntegrationTest extends BaseTest {

    private DuckdbParametersSpec duckdbParametersSpec;
    private String schemaName = "files";

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);

        this.duckdbParametersSpec = new DuckdbParametersSpec();
        duckdbParametersSpec.setUser(secretValueProvider.expandValue("${DQOPS_TEST_AWS_ACCESS_KEY_ID}", secretValueLookupContext));
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue("${DQOPS_TEST_AWS_SECRET_ACCESS_KEY}", secretValueLookupContext));
        duckdbParametersSpec.setRegion(secretValueProvider.expandValue("${DQOPS_TEST_AWS_REGION}", secretValueLookupContext));
        duckdbParametersSpec.setFilesFormatType(DuckdbFilesFormatType.csv);
    }

    @Test
    void listTables_straightlyFromBucket_returnFileAndFolder() {
        duckdbParametersSpec.getDirectories().put(schemaName, "s3://dqops-duckdb-test/");

        List<SourceTableModel> sourceTableModels = AwsTablesLister.listTables(duckdbParametersSpec, schemaName);

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
        duckdbParametersSpec.getDirectories().put(schemaName, "s3://dqops-duckdb-test");

        List<SourceTableModel> sourceTableModels = AwsTablesLister.listTables(duckdbParametersSpec, schemaName);

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
        duckdbParametersSpec.getDirectories().put(schemaName, "s3://dqops-duckdb-test/test-folder-1/data-set-1/");

        List<SourceTableModel> sourceTableModels = AwsTablesLister.listTables(duckdbParametersSpec, schemaName);

        assertThat(sourceTableModels)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .containsExactly(
                        "continuous_days_one_row_per_day.csv"
                );
    }

}