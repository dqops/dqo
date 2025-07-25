/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.duckdb.connectors.fileslisting;

import com.dqops.BaseTest;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.TablesListerProviderObjectMother;
import com.dqops.connectors.duckdb.fileslisting.google.GcsTablesLister;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GoogleTablesListerIntegrationTest extends BaseTest {

    private DuckdbParametersSpec duckdbParametersSpec;
    private String schemaName = "files";
    private GcsTablesLister sut;

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        SecretValueProviderImpl secretValueProvider = beanFactory.getBean(SecretValueProviderImpl.class);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);

        this.sut = (GcsTablesLister) TablesListerProviderObjectMother.getProvider().createTablesLister(DuckdbStorageType.gcs);

        this.duckdbParametersSpec = new DuckdbParametersSpec();
        duckdbParametersSpec.setUser(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.GCS_DQO_AI_TESTING_INTEROPERABILITY_ACCESS_KEY, secretValueLookupContext));
        duckdbParametersSpec.setPassword(secretValueProvider.expandValue(DevelopmentCredentialsSecretNames.GCS_DQO_AI_TESTING_INTEROPERABILITY_SECRET, secretValueLookupContext));
        duckdbParametersSpec.setFilesFormatType(DuckdbFilesFormatType.csv);
    }

    @Test
    void listTables_straightlyFromBucket_returnFileAndFolder() {
        duckdbParametersSpec.getDirectories().put(schemaName, "gs://dqops-duckdb-test/");

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
        duckdbParametersSpec.getDirectories().put(schemaName, "gs://dqops-duckdb-test");

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
        duckdbParametersSpec.getDirectories().put(schemaName, "gs://dqops-duckdb-test/test-folder-1/data-set-1/");

        List<SourceTableModel> sourceTableModels = sut.listTables(duckdbParametersSpec, schemaName, null, 300);

        assertThat(sourceTableModels)
                .extracting(sourceTableModel -> sourceTableModel.getTableName().getTableName())
                .containsExactly(
                        "continuous_days_one_row_per_day.csv"
                );
    }

    @Test
    void listTables_passedNotGoogleScheme_throwsException() {
        duckdbParametersSpec.getDirectories().put(schemaName, "s3://dqops-duckdb-test/test-folder-1/data-set-1/");

        assertThrows(RuntimeException.class, ()->{
            sut.listTables(duckdbParametersSpec, schemaName, null, 300);
        });
    }


}