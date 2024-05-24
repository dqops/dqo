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

package com.dqops.checks.defaults;

import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.userhome.UserHome;

/**
 * Service that will apply the default configuration of the data observability (the default checks) on new tables and columns that are imported.
 */
public interface DefaultObservabilityConfigurationService {
    /**
     * Applies the default configuration of default checks on a table and its columns.
     *
     * @param connectionSpec          Target connection specification.
     * @param targetTableSpec         Target table specification.
     * @param userHome                User home, to read the configuration.
     */
    void applyDefaultChecksOnTableAndColumns(ConnectionSpec connectionSpec,
                                             TableSpec targetTableSpec,
                                             UserHome userHome);

    /**
     * Applies the default configuration of default checks on a table only, not on columns.
     *
     * @param connectionSpec          Connection specification.
     * @param targetTableSpec         Target table specification.
     * @param userHome                User home, to read the configuration.
     */
    void applyDefaultChecksOnTableOnly(ConnectionSpec connectionSpec, TableSpec targetTableSpec, UserHome userHome);

    /**
     * Applies the default configuration of default checks on a column.
     *
     * @param connectionSpec          Connection specification.
     * @param targetTableSpec         Target table specification.
     * @param targetColumnSpec        Target column specification.
     * @param userHome                User home, to read the configuration.
     */
    void applyDefaultChecksOnColumn(ConnectionSpec connectionSpec,
                                    TableSpec targetTableSpec,
                                    ColumnSpec targetColumnSpec,
                                    UserHome userHome);
}
