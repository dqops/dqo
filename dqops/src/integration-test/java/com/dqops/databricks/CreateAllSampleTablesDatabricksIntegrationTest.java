/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.databricks;

import com.dqops.connectors.ProviderType;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateAllSampleTablesDatabricksIntegrationTest extends BaseDatabricksIntegrationTest {
    @Test
    void runTest_whenTestDataFileNamedContinuousDaysOneRowPerDayIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysOneRowPerDay13NonNegativeFloatsIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day_13_non_negative_floats, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysDifferentTimeDataTypesIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_different_time_data_types, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataRegexSensorIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_regex_sensor, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataTimelinessSensorsIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_timeliness_sensors, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysDataAndStringFormatsIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataValuesInSetIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataTimeSeriesIsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_time_series, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTest_string_test_data_IsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.string_test_data, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTest_value_match_left_table_IsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.value_match_left_table, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTest_geographic_coordinate_system_test_IsCreated_thenPutItInDatabricks() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.geographic_coordinate_system_test, ProviderType.databricks);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }
}
