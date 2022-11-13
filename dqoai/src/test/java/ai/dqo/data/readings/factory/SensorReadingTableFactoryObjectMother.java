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
package ai.dqo.data.readings.factory;

import tech.tablesaw.api.Table;

/**
 * Object mother for {@link SensorReadingsTableFactory}
 */
public class SensorReadingTableFactoryObjectMother {
    /**
     * Returns a new factory.
     * @return Factory.
     */
    public static SensorReadingsTableFactory createFactory() {
        return new SensorReadingsTableFactoryImpl();
    }

    /**
     * Creates an empty normalized table.
     * @param tableName Table name.
     * @return Empty normalized table with a proper schema.
     */
    public static Table createEmptyNormalizedTable(String tableName) {
        SensorReadingsTableFactory factory = createFactory();
        return factory.createEmptySensorReadoutsTable(tableName);
    }
}
