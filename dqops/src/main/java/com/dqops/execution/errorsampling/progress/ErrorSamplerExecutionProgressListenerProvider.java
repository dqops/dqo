/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.errorsampling.progress;

/**
 * Error sampler execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
public interface ErrorSamplerExecutionProgressListenerProvider {
    /**
     * Returns an error sampler execution progress listener for the requested reporting level.
     *
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the error samplers to the console.
     * @return Error sampler execution progress listener.
     */
    ErrorSamplerExecutionProgressListener getProgressListener(ErrorSamplerExecutionReportingMode reportingMode,
                                                              boolean writeSummaryToConsole);
}
