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

import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternSpec;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternSpec;

/**
 * Factory that creates the default configuration of checks, when DQOps is initialized and the initial configuration is loaded into the local settings.
 */
public interface DefaultObservabilityCheckSettingsFactory {
    /**
     * Creates the default check settings to be stored in the local settings. This is an initial, default configuration.
     *
     * @return Default observability settings.
     */
    DefaultObservabilityChecksSpec createDefaultCheckSettings();

    /**
     * Create an initial configuration of table-level checks.
     * @return The configuration of the default table level checks.
     */
    TableDefaultChecksPatternSpec createDefaultTableChecks();

    /**
     * Create an initial configuration of column-level checks.
     * @return The configuration of the default column level checks.
     */
    ColumnDefaultChecksPatternSpec createDefaultColumnChecks();
}
