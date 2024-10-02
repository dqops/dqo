/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.db2;

import com.dqops.connectors.ProviderType;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateAllSampleTablesDb2IntegrationTest extends BaseDb2IntegrationTest {
    @Test
    void runTest_whenTestDataFileNamedContinuousDaysOneRowPerDayIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysOneRowPerDay13NonNegativeFloatsIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day_13_non_negative_floats, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysDifferentTimeDataTypesIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_different_time_data_types, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataRegexSensorIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_regex_sensor, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataTimelinessSensorsIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_timeliness_sensors, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedContinuousDaysDataAndStringFormatsIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataValuesInSetIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTestDataTimeSeriesIsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_time_series, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTest_string_test_data_IsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.string_test_data, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTest_value_match_left_table_IsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.value_match_left_table, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }

    @Test
    void runTest_whenTestDataFileNamedTest_geographic_coordinate_system_test_IsCreated_thenPutItInDb2() {

        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.geographic_coordinate_system_test, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
    }
}
