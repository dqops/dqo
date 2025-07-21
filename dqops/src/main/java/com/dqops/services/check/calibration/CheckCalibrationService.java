/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.calibration;

import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.userhome.UserHome;

/**
 * Data quality check calibration service that can disable data quality checks or reconfigure data quality check thresholds.
 */
public interface CheckCalibrationService {
    /**
     * Disable checks matching the filters. The checks are marked as disabled and not removed from the table specification.
     * It also disables checks that were applied by policies (default check patterns).
     *
     * @param checkSearchFilters     Check search filter to apply. Must specify the connection name and table name.
     * @param userHome               User home used to find the default checks and the target tables.
     * @param disableProfilingChecks Boolean flag that decides to also disable profiling checks if they match the check search filter.
     */
    void disableChecks(CheckSearchFilters checkSearchFilters, UserHome userHome, boolean disableProfilingChecks);

    /**
     * Decreases the sensitivity of the check by changing the rule severity for all matching checks by changing the rule parameters.
     * @param checkSearchFilters Check search filter to identify target tables to change.
     * @param userHome User home to find default checks and all required objects (connection, etc).
     * @param reconfigureProfilingChecks When true, also reconfigures profiling checks.
     */
    void decreaseCheckSensitivity(CheckSearchFilters checkSearchFilters, UserHome userHome, boolean reconfigureProfilingChecks);
}
