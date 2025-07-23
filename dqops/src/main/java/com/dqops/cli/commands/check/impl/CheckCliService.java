/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.check.impl;

import com.dqops.cli.commands.check.impl.models.AllChecksModelCliPatchParameters;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.RunChecksTarget;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.services.check.mapping.models.AllChecksModel;

import java.util.List;

/**
 * Service called from CLI to run checks or operate on checks.
 */
public interface CheckCliService {
    /**
     * Runs checks given the filters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilterParameters Optional user provided time window parameters, limits the time period that is analyzed.
     * @param collectErrorSamples Collect error samples for failed checks.
     * @param checkExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @param executionTarget Execution target (sensor, rule, both).
     * @return Check execution summary.
     */
    CheckExecutionSummary runChecks(CheckSearchFilters checkSearchFilters,
                                    TimeWindowFilterParameters timeWindowFilterParameters,
                                    boolean collectErrorSamples,
                                    CheckExecutionProgressListener checkExecutionProgressListener,
                                    boolean dummyRun,
                                    RunChecksTarget executionTarget);

    /**
     * Collects error samples for data quality checks identified by filters.
     * @param checkSearchFilters Check search filters.
     * @param timeWindowFilterParameters Optional user provided time window parameters, limits the time period that is analyzed.
     * @param errorSamplesDataScope Error sampling data scope - a whole table, or each data grouping.
     * @param errorSamplerExecutionProgressListener Progress listener that will report the progress.
     * @param dummyRun Run the sensors in a dummy mode (sensors are not executed).
     * @return Error sampler execution summary.
     */
    ErrorSamplerExecutionSummary collectErrorSamples(CheckSearchFilters checkSearchFilters,
                                                     TimeWindowFilterParameters timeWindowFilterParameters,
                                                     ErrorSamplesDataScope errorSamplesDataScope,
                                                     ErrorSamplerExecutionProgressListener errorSamplerExecutionProgressListener,
                                                     boolean dummyRun);

    /**
     * Deactivates existing checks matching the provided filters.
     * @param filters Check search filters to find checks to disable.
     */
    void deactivateChecks(CheckSearchFilters filters);

    /**
     * Update checks configuration based on provided parameters.
     * @param parameters Parameters for creating the patches and updating.
     * @return List of patches (by connections) of the updated configuration of all checks.
     */
    List<AllChecksModel> updateAllChecksPatch(AllChecksModelCliPatchParameters parameters);
}
