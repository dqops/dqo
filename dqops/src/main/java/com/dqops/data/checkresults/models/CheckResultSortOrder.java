/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.models;

import com.dqops.utils.docs.generators.SampleValueFactory;

/**
 * Enumeration of columns names on a {@link CheckResultEntryModel} that can be sorted.
 */
public enum CheckResultSortOrder {
    executedAt,
    checkHash,
    checkCategory,
    checkName,
    checkDisplayName,
    checkType,
    actualValue,
    expectedValue,
    severity,
    columnName,
    dataGroup,
    timeGradient,
    timePeriod,
    qualityDimension,
    sensorName,
    updatedAt;

    public static class CheckResultSortOrderSampleFactory implements SampleValueFactory<CheckResultSortOrder> {
        @Override
        public CheckResultSortOrder createSample() {
            return executedAt;
        }
    }
}
