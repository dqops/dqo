/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
