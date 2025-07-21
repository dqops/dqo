/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.athena;

import com.dqops.connectors.trino.AthenaConnectionSpecObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateAllSampleTablesAthenaIntegrationTest extends BaseAthenaIntegrationTest {

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysOneRowPerDayIsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_one_row_per_day, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysOneRowPerDay13NonNegativeFloatsIsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_one_row_per_day_13_non_negative_floats, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysDifferentTimeDataTypesIsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_different_time_data_types, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataRegexSensorIsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.test_data_regex_sensor, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysDataAndStringFormatsIsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.continuous_days_date_and_string_formats, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataValuesInSetIsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.test_data_values_in_set, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataTimeSeriesIsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.test_data_time_series, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTest_string_test_data_IsCreated_thenPutItInAthena() {
        ConnectionSpec connectionSpec = AthenaConnectionSpecObjectMother.create();

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.string_test_data, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }
}
