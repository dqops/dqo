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
package com.dqops.data.errors.factory;

import com.dqops.data.readouts.factory.SensorReadoutsTableFactoryImpl;
import tech.tablesaw.api.Table;

/**
 * Object mother for {@link ErrorsTableFactory}.
 */
public class ErrorsTableFactoryObjectMother {
    /**
     * Returns a new factory.
     * @return Factory.
     */
    public static ErrorsTableFactory createFactory() {
        return new ErrorsTableFactoryImpl(new SensorReadoutsTableFactoryImpl());
    }

    /**
     * Creates an empty normalized table.
     * @param tableName Table name.
     * @return Empty normalized table with a proper schema.
     */
    public static Table createEmptyNormalizedTable(String tableName) {
        ErrorsTableFactory factory = createFactory();
        return factory.createEmptyErrorsTable(tableName);
    }
}
