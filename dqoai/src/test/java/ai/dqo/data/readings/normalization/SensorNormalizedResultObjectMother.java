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
package ai.dqo.data.readings.normalization;

import ai.dqo.data.readings.factory.SensorReadingTableFactoryObjectMother;
import tech.tablesaw.api.Table;

/**
 * Object mother for {@link SensorNormalizedResult}.
 */
public class SensorNormalizedResultObjectMother {
    /**
     * Creates an empty normalized results object with an empty table.
     * @return Empty normalized results.
     */
    public static SensorNormalizedResult createEmptyNormalizedResults() {
        Table emptyNormalizedResultsTable = SensorReadingTableFactoryObjectMother.createEmptyNormalizedTable("empty");
        return new SensorNormalizedResult(emptyNormalizedResultsTable);
    }
}
