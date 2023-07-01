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
