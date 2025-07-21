/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.normalization;

import com.dqops.data.readouts.factory.SensorReadoutTableFactoryObjectMother;
import tech.tablesaw.api.Table;

/**
 * Object mother for {@link SensorReadoutsNormalizedResult}.
 */
public class SensorNormalizedResultObjectMother {
    /**
     * Creates an empty normalized results object with an empty table.
     * @return Empty normalized results.
     */
    public static SensorReadoutsNormalizedResult createEmptyNormalizedResults() {
        Table emptyNormalizedResultsTable = SensorReadoutTableFactoryObjectMother.createEmptyNormalizedTable("empty");
        return new SensorReadoutsNormalizedResult(emptyNormalizedResultsTable);
    }
}
