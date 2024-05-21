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
}
