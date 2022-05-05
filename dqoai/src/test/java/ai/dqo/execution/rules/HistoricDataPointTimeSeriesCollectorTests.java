/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.rules;

import ai.dqo.BaseTest;
import ai.dqo.data.readings.factory.SensorReadingTableFactoryObjectMother;
import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import ai.dqo.execution.sensors.SensorExecutionResultObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

@SpringBootTest
public class HistoricDataPointTimeSeriesCollectorTests extends BaseTest {
    private Table table;
    private SensorNormalizedResult normalizedResult;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.table = SensorReadingTableFactoryObjectMother.createEmptyNormalizedTable("tab");
		this.normalizedResult = new SensorNormalizedResult(this.table);
    }

    @Test
    void getHistoricDataPointsBefore_whenDayAndNoPreviousData_thenReturnsEmptyArray() {
        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimeSeriesGradient.DAY, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricDataPointsBefore(LocalDateTime.of(2022, 1, 15, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);
        Assertions.assertTrue(Arrays.stream(dataPoints).allMatch(p -> p == null));
    }

    @Test
    void getHistoricDataPointsBefore_whenPreviousDataPresent_thenReturnsAllPreviousDays() {
        for( int i = 1; i <= 30; i++) {
            int rowNumber = this.table.appendRow().getRowNumber();
			this.normalizedResult.getActualValueColumn().set(rowNumber, i + 100.0);
			this.normalizedResult.getTimePeriodColumn().set(rowNumber,
                    LocalDateTime.of(2022, 1, i, 0, 0, 0));
        }

        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimeSeriesGradient.DAY, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricDataPointsBefore(LocalDateTime.of(2022, 1, 20, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);
        Assertions.assertTrue(Arrays.stream(dataPoints).allMatch(p -> p != null));

        for(int i = 0; i < 14; i++) {
            Assertions.assertEquals(106.0 + i, dataPoints[i].getSensorReading());
            Assertions.assertEquals(LocalDateTime.of(2022, 1, i + 6, 0, 0, 0), dataPoints[i].getLocalDatetime());
            Assertions.assertEquals(-(14 - i), dataPoints[i].getBackPeriodsIndex());
        }
    }
}
