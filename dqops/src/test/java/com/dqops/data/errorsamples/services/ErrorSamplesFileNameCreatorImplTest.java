/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.errorsamples.services;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ErrorSamplesFileNameCreatorImplTest extends BaseTest {

    private ErrorSamplesFileNameCreator sut;

    @BeforeEach
    void setUp() {
        this.sut = ErrorSamplesFileNameCreatorObjectMother.getDefault();
    }

    @Test
    void createFileName_whenTableErrorSamples_thenCreatesName() {

        ErrorSamplesFileNameDetails errorSamplesFileNameDetails = ErrorSamplesFileNameDetails.builder()
                .connectionName("conn")
                .schemaName("sch")
                .tableName("tab")
                .checkCategory("volume")
                .checkName("daily_row_count").build();

        LocalDateTime dateTime = LocalDateTime.of(2024, 9, 1, 8, 30, 56);

        String result = this.sut.createFileName(errorSamplesFileNameDetails, dateTime);

        Assertions.assertEquals("Error_samples_conn.sch.tab_volume_daily_row_count_2024-09-01_08:30:56.csv",
                result);
    }

    @Test
    void createFileName_whenColumnErrorSamples_thenCreatesName() {

        ErrorSamplesFileNameDetails errorSamplesFileNameDetails = ErrorSamplesFileNameDetails.builder()
                .connectionName("conn")
                .schemaName("sch")
                .tableName("tab")
                .columnName("col")
                .checkCategory("volume")
                .checkName("daily_row_count").build();

        LocalDateTime dateTime = LocalDateTime.of(2024, 9, 1, 8, 30, 56);

        String result = this.sut.createFileName(errorSamplesFileNameDetails, dateTime);

        Assertions.assertEquals("Error_samples_conn.sch.tab.col_volume_daily_row_count_2024-09-01_08:30:56.csv",
                result);
    }

    @Test
    void createFileName_whenNoCategoryAndCheckName_thenCreatesName() {

        ErrorSamplesFileNameDetails errorSamplesFileNameDetails = ErrorSamplesFileNameDetails.builder()
                .connectionName("conn")
                .schemaName("sch")
                .tableName("tab").build();

        LocalDateTime dateTime = LocalDateTime.of(2024, 9, 1, 8, 30, 56);

        String result = this.sut.createFileName(errorSamplesFileNameDetails, dateTime);

        Assertions.assertEquals("Error_samples_conn.sch.tab_2024-09-01_08:30:56.csv",
                result);
    }

}