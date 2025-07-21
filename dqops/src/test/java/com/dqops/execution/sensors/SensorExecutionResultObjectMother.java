/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors;

import com.dqops.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import tech.tablesaw.api.Table;

/**
 * Object mother for SensorExecutionResult.
 */
public class SensorExecutionResultObjectMother {
    /**
     * Creates an empty normalized results with an empty, normalized table.
     * @return Empty results table.
     */
    public static SensorExecutionResult createEmptyNormalizedResults() {
        Table table = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("normalized_results");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createEmptyBigQuery();
        return new SensorExecutionResult(runParameters, table);
    }
}
