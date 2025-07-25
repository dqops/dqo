/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules;

import com.dqops.BaseTest;
import com.dqops.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;

@SpringBootTest
public class HistoricDataPointTimeSeriesCollectorTests extends BaseTest {
    private Table table;
    private SensorReadoutsNormalizedResult normalizedResult;

    @BeforeEach
    void setUp() {
		this.table = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("tab");
		this.normalizedResult = new SensorReadoutsNormalizedResult(this.table);
    }

    @Test
    void getHistoricDataPointsBefore_whenDayAndNoPreviousData_thenReturnsEmptyArray() {
        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimePeriodGradient.day, ZoneId.of("UTC"));
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

        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimePeriodGradient.day, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricDataPointsBefore(LocalDateTime.of(2022, 1, 20, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);
        Assertions.assertTrue(Arrays.stream(dataPoints).allMatch(p -> p != null));

        for(int i = 0; i < 14; i++) {
            Assertions.assertEquals(106.0 + i, dataPoints[i].getSensorReadout());
            Assertions.assertEquals(LocalDateTime.of(2022, 1, i + 6, 0, 0, 0),
                    LocalDateTime.ofEpochSecond(dataPoints[i].getLocalDatetimeEpoch(), 0, ZoneOffset.UTC));
            Assertions.assertEquals(-(14 - i), dataPoints[i].getBackPeriodsIndex());
        }
    }

    @Test
    void getHistoricDataPointsBefore_whenPreviousDataPresentButSomeAreMissing_thenReturnsAllPreviousDaysWithNullsForMissingPeriods() {
        for( int i = 1; i <= 30; i++) {
            if (i != 15 && i != 16) {
                int rowNumber = this.table.appendRow().getRowNumber();
                this.normalizedResult.getActualValueColumn().set(rowNumber, i + 100.0);
                this.normalizedResult.getTimePeriodColumn().set(rowNumber,
                        LocalDateTime.of(2022, 1, i, 0, 0, 0));
            }
        }

        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimePeriodGradient.day, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricDataPointsBefore(LocalDateTime.of(2022, 1, 20, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);

        for(int i = 0; i < 14; i++) {
            if (i == 9 || i == 10) {
                Assertions.assertNull(dataPoints[i]);
            }
            else {
                Assertions.assertEquals(106.0 + i, dataPoints[i].getSensorReadout(), "At index: " + i);
                Assertions.assertEquals(LocalDateTime.of(2022, 1, i + 6, 0, 0, 0),
                        LocalDateTime.ofEpochSecond(dataPoints[i].getLocalDatetimeEpoch(), 0, ZoneOffset.UTC));
                Assertions.assertEquals(-(14 - i), dataPoints[i].getBackPeriodsIndex());
            }
        }
    }

    @Test
    void getHistoricDataPointsBefore_whenNotAllPreviousDataPresent_thenReturnsNullsFollowedByPreviousDays() {
        for( int i = 15; i <= 30; i++) {
            int rowNumber = this.table.appendRow().getRowNumber();
            this.normalizedResult.getActualValueColumn().set(rowNumber, i + 100.0);
            this.normalizedResult.getTimePeriodColumn().set(rowNumber,
                    LocalDateTime.of(2022, 1, i, 0, 0, 0));
        }

        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimePeriodGradient.day, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricDataPointsBefore(LocalDateTime.of(2022, 1, 20, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);

        for(int i = 0; i < 9; i++) {
            Assertions.assertNull(dataPoints[i]);
        }

        for(int i = 10; i < 14; i++) {
            Assertions.assertEquals(106.0 + i, dataPoints[i].getSensorReadout());
            Assertions.assertEquals(LocalDateTime.of(2022, 1, i + 6, 0, 0, 0),
                    LocalDateTime.ofEpochSecond(dataPoints[i].getLocalDatetimeEpoch(), 0, ZoneOffset.UTC));
            Assertions.assertEquals(-(14 - i), dataPoints[i].getBackPeriodsIndex());
        }
    }

    @Test
    void getHistoricContinuousResultsBefore_whenNoPreviousData_thenReturnsEmptyArray() {
        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimePeriodGradient.day, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricContinuousResultsBefore(LocalDateTime.of(2022, 1, 15, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);
        Assertions.assertTrue(Arrays.stream(dataPoints).allMatch(p -> p == null));
    }

    @Test
    void getHistoricContinuousResultsBefore_whenLessThanRequestedValuesPresent_thenReturnsOlderValuesAsNulls() {
        for( int i = 1; i <= 10; i++) {
            int rowNumber = this.table.appendRow().getRowNumber();
            this.normalizedResult.getActualValueColumn().set(rowNumber, i + 100.0);
            this.normalizedResult.getTimePeriodColumn().set(rowNumber,
                    LocalDateTime.of(2022, 1, i, 0, 0, 0));
        }

        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimePeriodGradient.day, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricContinuousResultsBefore(LocalDateTime.of(2022, 1, 20, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);

        for(int i = 0; i < 4; i++) {
            Assertions.assertNull(dataPoints[i], "At index: " + i);
        }

        for(int i = 4; i < 14; i++) {
            Assertions.assertEquals(101.0 + i - 4, dataPoints[i].getSensorReadout());
            Assertions.assertEquals(LocalDateTime.of(2022, 1, i - 4 + 1, 0, 0, 0),
                    LocalDateTime.ofEpochSecond(dataPoints[i].getLocalDatetimeEpoch(), 0, ZoneOffset.UTC));
            Assertions.assertEquals(-(14 - i), dataPoints[i].getBackPeriodsIndex());
        }
    }

    @Test
    void getHistoricContinuousResultsBefore_whenMoreThanRequestedValuesPresent_thenReturnsRequestedRecentValues() {
        for( int i = 1; i <= 30; i++) {
            int rowNumber = this.table.appendRow().getRowNumber();
            this.normalizedResult.getActualValueColumn().set(rowNumber, i + 100.0);
            this.normalizedResult.getTimePeriodColumn().set(rowNumber,
                    LocalDateTime.of(2022, 1, i, 0, 0, 0));
        }

        HistoricDataPointTimeSeriesCollector sut = new HistoricDataPointTimeSeriesCollector(this.table, TimePeriodGradient.day, ZoneId.of("UTC"));
        HistoricDataPoint[] dataPoints = sut.getHistoricContinuousResultsBefore(LocalDateTime.of(2022, 1, 20, 0, 0, 0), 14);
        Assertions.assertNotNull(dataPoints);
        Assertions.assertEquals(14, dataPoints.length);

        for(int i = 1; i < 14; i++) {
            Assertions.assertEquals(101.0 + i + 5, dataPoints[i].getSensorReadout());
            Assertions.assertEquals(LocalDateTime.of(2022, 1, i + 5 + 1, 0, 0, 0),
                    LocalDateTime.ofEpochSecond(dataPoints[i].getLocalDatetimeEpoch(), 0, ZoneOffset.UTC));
            Assertions.assertEquals(-(14 - i), dataPoints[i].getBackPeriodsIndex());
        }
    }
}
