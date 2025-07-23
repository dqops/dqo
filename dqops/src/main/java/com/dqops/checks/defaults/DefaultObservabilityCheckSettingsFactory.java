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

import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.table.TableQualityPolicySpec;

import java.util.List;

/**
 * Factory that creates the default configuration of checks, when DQOps is initialized and the initial configuration is loaded into the local settings.
 */
public interface DefaultObservabilityCheckSettingsFactory {
    /**
     * Create an initial configuration of table-level checks (data quality policies).
     * @return The configuration of the default table level checks.
     */
    List<TableQualityPolicySpec> createDefaultTableQualityPolicies();

    /**
     * Create an initial configuration of column-level checks (data quality policies).
     * @return The configuration of the default column level checks.
     */
    List<ColumnQualityPolicySpec> createDefaultColumnQualityPolicies();
}
